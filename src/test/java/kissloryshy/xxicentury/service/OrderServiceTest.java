package kissloryshy.xxicentury.service;

import kissloryshy.xxicentury.entity.Order;
import kissloryshy.xxicentury.exception.OrderNotFoundException;
import kissloryshy.xxicentury.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void create() {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());
        Assertions.assertDoesNotThrow(() -> orderService.create(o1));
        verify(orderRepository).save(o1);
        when(orderRepository.findById(o1.getId())).thenReturn(Optional.of(o1));

        Assertions.assertEquals(Optional.of(o1), orderService.getById(o1.getId()));
        verify(orderRepository).findById(o1.getId());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void update() {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());
        when(orderRepository.findById(o1.getId())).thenReturn(Optional.of(o1));
        Assertions.assertDoesNotThrow(() -> orderService.update(o1.getId(), o1));
        verify(orderRepository).save(o1);
    }

    @Test
    void delete() {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());
        Assertions.assertThrows(OrderNotFoundException.class,
                () -> orderService.delete(1000L));

        when(orderRepository.findById(o1.getId())).thenReturn(Optional.of(o1));
        Assertions.assertDoesNotThrow(() -> orderService.delete(o1.getId()));

        verify(orderRepository).delete(o1);
        verify(orderRepository).findById(o1.getId());
    }

    @Test
    void getAll() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>()));
        orders.add(new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>()));

        when(orderRepository.findAll()).thenReturn(orders);
        Assertions.assertTrue(orderService.getAll().containsAll(orders));
        verify(orderRepository).findAll();
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void getById() {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());

        when(orderRepository.findById(o1.getId())).thenReturn(Optional.of(o1));
        Assertions.assertEquals(Optional.of(o1), orderService.getById(o1.getId()));

        verify(orderRepository).findById(o1.getId());
        verifyNoMoreInteractions(orderRepository);
    }
}