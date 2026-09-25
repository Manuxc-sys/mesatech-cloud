package mesatech.bff_service.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import mesatech.bff_service.client.CategoriaClient;
import mesatech.bff_service.client.PrioridadClient;
import mesatech.bff_service.client.SolicitudClient;
import mesatech.bff_service.dto.CategoriaDTO;
import mesatech.bff_service.dto.PrioridadDTO;
import mesatech.bff_service.dto.SolicitudDTO;
import mesatech.bff_service.dto.SolicitudDetalleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff")
@CrossOrigin(origins = "*") // Habilitado para React
@RequiredArgsConstructor
public class BffController {

    private final SolicitudClient solicitudClient;
    private final CategoriaClient categoriaClient;
    private final PrioridadClient prioridadClient;

    // ==========================================
    // 1. ENDPOINTS PARA SOLICITUDES / TICKETS
    // ==========================================

    @GetMapping("/solicitudes")
    public ResponseEntity<List<SolicitudDetalleDTO>> listarTodas() {
        List<SolicitudDTO> solicitudes = solicitudClient.listarTodas();

        List<SolicitudDetalleDTO> detalleList = solicitudes.stream().map(solicitud -> {
            SolicitudDetalleDTO detalle = new SolicitudDetalleDTO();
            detalle.setId(solicitud.getId());
            detalle.setTitulo(solicitud.getTitulo());
            detalle.setDescripcion(solicitud.getDescripcion()); // <-- NUEVO
            detalle.setEstado(solicitud.getEstado());
            detalle.setUsuarioSolicitante(solicitud.getUsuarioSolicitante()); // <-- NUEVO
            detalle.setCategoria(obtenerNombreCategoria(solicitud.getCategoriaId()));
            detalle.setPrioridad(obtenerNombrePrioridad(solicitud.getPrioridadId()));

            return detalle;
        }).toList();

        return ResponseEntity.ok(detalleList);
    }

    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<SolicitudDetalleDTO> obtenerDetalleUnificado(@PathVariable Long id) {
        SolicitudDTO solicitudCruda = solicitudClient.obtenerSolicitudPorId(id);

        String categoria = obtenerNombreCategoria(solicitudCruda.getCategoriaId());
        String prioridad = obtenerNombrePrioridad(solicitudCruda.getPrioridadId());

        SolicitudDetalleDTO detalleFinal = new SolicitudDetalleDTO();
        detalleFinal.setId(solicitudCruda.getId());
        detalleFinal.setTitulo(solicitudCruda.getTitulo());
        detalleFinal.setEstado(solicitudCruda.getEstado());
        detalleFinal.setCategoria(categoria);
        detalleFinal.setPrioridad(prioridad);

        return ResponseEntity.ok(detalleFinal);
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<SolicitudDTO> crear(@RequestBody SolicitudDTO solicitud) {
        SolicitudDTO creada = solicitudClient.crearSolicitud(solicitud);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/solicitudes/{id}")
    public ResponseEntity<SolicitudDTO> actualizar(@PathVariable Long id, @RequestBody SolicitudDTO solicitud) {
        SolicitudDTO actualizada = solicitudClient.actualizarSolicitud(id, solicitud);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/solicitudes/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudClient.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // 2. ENDPOINTS QUE FALTABAN PARA EL CATÁLOGO
    // (Para que React llene sus dropdowns/selects)
    // ==========================================

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        try {
            return ResponseEntity.ok(categoriaClient.obtenerCategorias());
        } catch (FeignException e) {
            return ResponseEntity.ok(List.of());
        }
    }

    @GetMapping("/prioridades")
    public ResponseEntity<List<PrioridadDTO>> listarPrioridades() {
        try {
            return ResponseEntity.ok(prioridadClient.obtenerPrioridades());
        } catch (FeignException e) {
            return ResponseEntity.ok(List.of());
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES CON CONTROL DE EXCEPCIONES
    // ==========================================

    private String obtenerNombreCategoria(Long categoriaId) {
        if (categoriaId == null) {
            return "Sin Categoría";
        }
        try {
            CategoriaDTO categoria = categoriaClient.obtenerCategoriaPorId(categoriaId);
            return (categoria != null && categoria.getDescripcion() != null)
                    ? categoria.getDescripcion()
                    : "Sin Categoría";
        } catch (FeignException.NotFound e) {
            return "Sin Categoría";
        } catch (Exception e) {
            return "Error al obtener categoría";
        }
    }

    private String obtenerNombrePrioridad(Long prioridadId) {
        if (prioridadId == null) {
            return "Sin Prioridad";
        }
        try {
            PrioridadDTO prioridad = prioridadClient.obtenerPrioridadPorId(prioridadId);
            return (prioridad != null && prioridad.getDescripcion() != null)
                    ? prioridad.getDescripcion()
                    : "Sin Prioridad";
        } catch (FeignException.NotFound e) {
            return "Sin Prioridad";
        } catch (Exception e) {
            return "Error al obtener prioridad";
        }
    }
}