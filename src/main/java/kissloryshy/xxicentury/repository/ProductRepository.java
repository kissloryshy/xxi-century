package kissloryshy.xxicentury.repository;

import kissloryshy.xxicentury.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    List<Product> findByIsDeletedFalse();

    @Transactional
    @Modifying
    @Query("update Product p set p.isDeleted = ?1 where p.id = ?2")
    void updateIsDeletedById(Boolean isDeleted, Long id);

}
