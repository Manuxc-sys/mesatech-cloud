package mesatech.ms_catalogo.service;

import lombok.RequiredArgsConstructor;
import mesatech.ms_catalogo.entity.Prioridad;
import mesatech.ms_catalogo.repository.PrioridadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrioridadService {

    private final PrioridadRepository prioridadRepository;

    public List<Prioridad> listarTodas() {
        return prioridadRepository.findAll();
    }

    public Optional<Prioridad> buscarPorId(Long id) {
        return prioridadRepository.findById(id);
    }

    public Prioridad crear(Prioridad prioridad) {
        return prioridadRepository.save(prioridad);
    }

    public Prioridad actualizar(Long id, Prioridad prioridad) {
        return prioridadRepository.findById(id)
                .map(existente -> {
                    existente.setDescripcion(prioridad.getDescripcion());
                    return prioridadRepository.save(existente);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        prioridadRepository.deleteById(id);
    }
}
