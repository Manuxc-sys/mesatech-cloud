package mesatech.bff_service.client;

import mesatech.bff_service.dto.CategoriaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", contextId = "categoriaClient", url = "${MS_CATALOGO_URL:http://localhost:8080}")
public interface CategoriaClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaDTO obtenerCategoriaPorId(@PathVariable("id") Long id);
}
