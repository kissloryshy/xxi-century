package kissloryshy.xxicentury.service;

import kissloryshy.xxicentury.entity.Product;
import kissloryshy.xxicentury.exception.ProductNotFoundException;
import kissloryshy.xxicentury.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void getById() {
        Product p1 = new Product(1L, "Mon", 13, false);

        when(productRepository.findById(p1.getId())).thenReturn(Optional.of(p1));
        Assertions.assertEquals(Optional.of(p1), productService.getById(p1.getId()));

        verify(productRepository).findById(p1.getId());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void getAll() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Mon", 13, false));
        products.add(new Product(2L, "Ph", 23, false));

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAll();

        assertNotNull(result);
    }

    @Test
    void create() {
        Product p1 = new Product(1L, "Mon", 13, false);
        Assertions.assertDoesNotThrow(() -> productService.create(p1));
        verify(productRepository).save(p1);
        when(productRepository.findById(p1.getId())).thenReturn(Optional.of(p1));

        Assertions.assertEquals(Optional.of(p1), productService.getById(p1.getId()));
        verify(productRepository).findById(p1.getId());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void update() {
        Product p1 = new Product(1L, "Mon", 13, false);
        when(productRepository.findById(p1.getId())).thenReturn(Optional.of(p1));
        Assertions.assertDoesNotThrow(() -> productService.update(p1.getId(), p1));
        verify(productRepository).save(p1);
    }

    @Test
    void delete() {
        Product p1 = new Product(1L, "Mon", 13, false);
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.delete(1000L));

        when(productRepository.findById(p1.getId())).thenReturn(Optional.of(p1));
        Assertions.assertDoesNotThrow(() -> productService.delete(p1.getId()));

        verify(productRepository).updateIsDeletedById(true, p1.getId());
    }
}