package mesatech.bff_service.client;

import mesatech.bff_service.dto.PrioridadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-catalogo-prioridad", url = "http://localhost:8080")
public interface PrioridadClient {

    @GetMapping("/api/prioridades")
    List<PrioridadDTO> obtenerPrioridades();

    @GetMapping("/api/prioridades/{id}")
    PrioridadDTO obtenerPrioridadPorId(@PathVariable("id") Long id);
}