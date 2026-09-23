package mesatech.ms_catalogo.service;

import lombok.RequiredArgsConstructor;
import mesatech.ms_catalogo.entity.Categoria;
import mesatech.ms_catalogo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria categoria) {
        return categoriaRepository.findById(id)
                .map(existente -> {
                    existente.setDescripcion(categoria.getDescripcion());
                    return categoriaRepository.save(existente);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
