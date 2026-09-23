package mesatech.bff_service.client;

import mesatech.bff_service.dto.PrioridadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", contextId = "prioridadClient", url = "${MS_CATALOGO_URL:http://localhost:8080}")
public interface PrioridadClient {

    @GetMapping("/api/prioridades/{id}")
    PrioridadDTO obtenerPrioridadPorId(@PathVariable("id") Long id);
}
