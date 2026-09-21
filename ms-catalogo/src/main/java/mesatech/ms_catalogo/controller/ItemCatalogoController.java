package mesatech.ms_catalogo.controller;

import lombok.RequiredArgsConstructor;
import mesatech.ms_catalogo.entity.ItemCatalogo;
import mesatech.ms_catalogo.service.ItemCatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class ItemCatalogoController {

    private final ItemCatalogoService itemCatalogoService;

    @GetMapping
    public ResponseEntity<List<ItemCatalogo>> listar() {
        return ResponseEntity.ok(itemCatalogoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCatalogo> obtenerPorId(@PathVariable Long id) {
        return itemCatalogoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemCatalogo> crear(@RequestBody ItemCatalogo item) {
        return ResponseEntity.ok(itemCatalogoService.guardar(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCatalogo> actualizar(@PathVariable Long id, @RequestBody ItemCatalogo item) {
        ItemCatalogo actualizado = itemCatalogoService.actualizar(id, item);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        itemCatalogoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
