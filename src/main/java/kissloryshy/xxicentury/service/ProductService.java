package kissloryshy.xxicentury.service;

import jakarta.transaction.Transactional;
import kissloryshy.xxicentury.entity.Product;
import kissloryshy.xxicentury.exception.ProductNotFoundException;
import kissloryshy.xxicentury.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> getById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getAll() {
        return productRepository.findByIsDeletedFalse();
    }

    public Product create(Product productDto) {
        return productRepository.save(productDto);
    }

    public Product update(Long productId, Product newProduct) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            return productRepository.save(product);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Transactional
    public void delete(Long productId) {
        productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.updateIsDeletedById(true, productId);
    }
}
