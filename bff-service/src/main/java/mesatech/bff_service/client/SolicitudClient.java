package mesatech.bff_service.client;

import mesatech.bff_service.dto.SolicitudDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-solicitudes", url = "${MS_SOLICITUDES_URL:http://localhost:8081}")
public interface SolicitudClient {
    @GetMapping("/api/solicitudes/{id}")
    SolicitudDTO obtenerSolicitudPorId(@PathVariable("id") Long id);
}