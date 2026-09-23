package mesatech.bff_service.controller;

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

@RestController
@RequestMapping("/api/bff/solicitudes")
@RequiredArgsConstructor
public class BffController {

    private final SolicitudClient solicitudClient;
    private final CategoriaClient categoriaClient;
    private final PrioridadClient prioridadClient;

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDetalleDTO> obtenerDetalleUnificado(@PathVariable Long id) {
        // 1. Vamos a buscar los datos crudos del ticket (Trae IDs)
        SolicitudDTO solicitudCruda = solicitudClient.obtenerSolicitudPorId(id);

        // 2. Vamos a buscar los textos reales al catálogo usando esos IDs
        CategoriaDTO categoria = categoriaClient.obtenerCategoriaPorId(solicitudCruda.getCategoriaId());
        PrioridadDTO prioridad = prioridadClient.obtenerPrioridadPorId(solicitudCruda.getPrioridadId());

        // 3. Ensamblamos el objeto final para el frontend
        SolicitudDetalleDTO detalleFinal = new SolicitudDetalleDTO();
        detalleFinal.setId(solicitudCruda.getId());
        detalleFinal.setTitulo(solicitudCruda.getTitulo());
        detalleFinal.setEstado(solicitudCruda.getEstado());

        // Inyectamos los textos
        detalleFinal.setCategoria(categoria != null ? categoria.getDescripcion() : null);
        detalleFinal.setPrioridad(prioridad != null ? prioridad.getDescripcion() : null);

        // 4. Se lo entregamos a la aplicación de React en una sola respuesta
        return ResponseEntity.ok(detalleFinal);
    }
}