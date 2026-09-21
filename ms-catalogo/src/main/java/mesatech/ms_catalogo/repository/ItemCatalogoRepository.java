package mesatech.ms_catalogo.repository;

import mesatech.ms_catalogo.entity.ItemCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCatalogoRepository extends JpaRepository<ItemCatalogo, Long> {
}
