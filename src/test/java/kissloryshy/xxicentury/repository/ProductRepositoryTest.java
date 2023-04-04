package kissloryshy.xxicentury.repository;

import kissloryshy.xxicentury.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void save() {
        Product p1 = new Product("testName", 256);
        Product savedProduct = productRepository.save(p1);

        assertNotNull(savedProduct);
        assertEquals(p1.getName(), savedProduct.getName());
        assertEquals(p1.getPrice(), savedProduct.getPrice());
    }

    @Test
    void findByIsDeletedFalse() {
        Product p1 = new Product(1L, "testName1", 256, false);
        Product p2 = new Product(2L, "testName2", 256, true);
        Product p3 = new Product(3L, "testName3", 256, false);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);

        assertEquals(2, productRepository.findByIsDeletedFalse().size());
    }

    @Test
    void updateIsDeletedById() {
        Product p1 = new Product(10L, "testName1", 256, false);
        productRepository.save(p1);

        assertEquals(false, productRepository.findAll().get(0).getIsDeleted());
        productRepository.updateIsDeletedById(true, 10L);
    }

}