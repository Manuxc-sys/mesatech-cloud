package mesatech.bff_service.client;

import mesatech.bff_service.dto.CategoriaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-catalogo-categoria", url = "http://localhost:8080")
public interface CategoriaClient {

    @GetMapping("/api/categorias")
    List<CategoriaDTO> obtenerCategorias();

    @GetMapping("/api/categorias/{id}")
    CategoriaDTO obtenerCategoriaPorId(@PathVariable("id") Long id);
}