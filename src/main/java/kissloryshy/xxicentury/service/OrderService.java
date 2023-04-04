package kissloryshy.xxicentury.service;

import kissloryshy.xxicentury.entity.Order;
import kissloryshy.xxicentury.exception.OrderNotFoundException;
import kissloryshy.xxicentury.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order create(Order orderDto) {
        return orderRepository.save(orderDto);
    }

    public Order update(Long orderId, Order order) {
        orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setId(orderId);
        return orderRepository.save(order);
    }

    public void delete(Long orderId) {
        orderRepository.delete(orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId)));
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
