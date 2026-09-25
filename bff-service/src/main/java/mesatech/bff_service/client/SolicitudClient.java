package mesatech.bff_service.client;

import mesatech.bff_service.dto.SolicitudDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-solicitudes", url = "${MS_SOLICITUDES_URL:http://localhost:8081}")
public interface SolicitudClient {

    @GetMapping("/api/solicitudes")
    List<SolicitudDTO> listarTodas();

    @GetMapping("/api/solicitudes/{id}")
    SolicitudDTO obtenerSolicitudPorId(@PathVariable("id") Long id);

    @PostMapping("/api/solicitudes")
    SolicitudDTO crearSolicitud(@RequestBody SolicitudDTO solicitud);

    @PutMapping("/api/solicitudes/{id}")
    SolicitudDTO actualizarSolicitud(@PathVariable("id") Long id, @RequestBody SolicitudDTO solicitud);

    @DeleteMapping("/api/solicitudes/{id}")
    void eliminarSolicitud(@PathVariable("id") Long id);
}