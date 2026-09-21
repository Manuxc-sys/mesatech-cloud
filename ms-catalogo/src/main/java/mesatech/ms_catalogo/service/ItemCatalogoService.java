package mesatech.ms_catalogo.service;

import lombok.RequiredArgsConstructor;
import mesatech.ms_catalogo.entity.ItemCatalogo;
import mesatech.ms_catalogo.repository.ItemCatalogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCatalogoService {

    private final ItemCatalogoRepository itemCatalogoRepository;

    public List<ItemCatalogo> listarTodos() {
        return itemCatalogoRepository.findAll();
    }

    public Optional<ItemCatalogo> buscarPorId(Long id) {
        return itemCatalogoRepository.findById(id);
    }

    public ItemCatalogo guardar(ItemCatalogo item) {
        return itemCatalogoRepository.save(item);
    }

    public ItemCatalogo actualizar(Long id, ItemCatalogo item) {
        return itemCatalogoRepository.findById(id)
                .map(existente -> {
                    existente.setTipo(item.getTipo());
                    existente.setDescripcion(item.getDescripcion());
                    return itemCatalogoRepository.save(existente);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        itemCatalogoRepository.deleteById(id);
    }
}
