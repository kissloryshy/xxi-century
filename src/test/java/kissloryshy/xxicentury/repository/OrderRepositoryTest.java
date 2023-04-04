package kissloryshy.xxicentury.repository;

import kissloryshy.xxicentury.entity.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void save() {
        Order order = new Order(1L, "cl1", LocalDate.now(), "add1", new ArrayList<>());
        orderRepository.save(order);
        assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    void delete() {
        Order order = new Order(1L, "cl1", LocalDate.now(), "add1", new ArrayList<>());
        orderRepository.save(order);
        orderRepository.delete(orderRepository.findById(1L).get());
        assertEquals(0, orderRepository.findAll().size());
    }

    @Test
    void update() {
        Order order = new Order(1L, "cl1", LocalDate.now(), "add1", new ArrayList<>());
        orderRepository.save(order);

        String newName = "new-name";

        Order savedOrder = orderRepository.findAll().get(0);
        savedOrder.setClient(newName);
        orderRepository.saveAndFlush(savedOrder);
        assertEquals(newName, orderRepository.findAll().get(0).getClient());
    }

}