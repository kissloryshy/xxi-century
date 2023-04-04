package kissloryshy.xxicentury.controller;

import kissloryshy.xxicentury.entity.Order;
import kissloryshy.xxicentury.exception.OrderNotFoundException;
import kissloryshy.xxicentury.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId)));
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(order));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> update(@PathVariable Long orderId, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.update(orderId, order));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> delete(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok().build();
    }
}
