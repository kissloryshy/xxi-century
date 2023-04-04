package kissloryshy.xxicentury.controller;

import kissloryshy.xxicentury.entity.Product;
import kissloryshy.xxicentury.exception.ProductNotFoundException;
import kissloryshy.xxicentury.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Collection<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getById(productId).orElseThrow(() ->
                new ProductNotFoundException(productId)));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@PathVariable Long productId, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(productId, product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
