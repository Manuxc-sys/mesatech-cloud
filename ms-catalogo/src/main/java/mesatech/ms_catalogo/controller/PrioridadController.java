package mesatech.ms_catalogo.controller;

import lombok.RequiredArgsConstructor;
import mesatech.ms_catalogo.entity.Prioridad;
import mesatech.ms_catalogo.service.PrioridadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prioridades")
@RequiredArgsConstructor
public class PrioridadController {

    private final PrioridadService prioridadService;

    @GetMapping
    public ResponseEntity<List<Prioridad>> listar() {
        return ResponseEntity.ok(prioridadService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prioridad> obtenerPorId(@PathVariable Long id) {
        return prioridadService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Prioridad> crear(@RequestBody Prioridad prioridad) {
        return ResponseEntity.ok(prioridadService.crear(prioridad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prioridad> actualizar(@PathVariable Long id, @RequestBody Prioridad prioridad) {
        Prioridad actualizada = prioridadService.actualizar(id, prioridad);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prioridadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
