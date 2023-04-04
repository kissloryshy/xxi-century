package kissloryshy.xxicentury.util;

import kissloryshy.xxicentury.entity.Order;
import kissloryshy.xxicentury.entity.OrderItem;
import kissloryshy.xxicentury.entity.Product;
import kissloryshy.xxicentury.service.OrderService;
import kissloryshy.xxicentury.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class InitDb {
    @Bean
    CommandLineRunner initTestData(ProductService productService, OrderService orderService) {
        Product p1 = new Product("Monitor", 14995);
        Product p2 = new Product("Phone", 29999);
        Product p3 = new Product("Headset", 2999);
        Product p4 = new Product("Keyboard", 7495);
        Product p5 = new Product("Mouse", 1995);

        Order o1 = new Order("Client1", LocalDate.now().minusDays(3), "Address1");
        Order o2 = new Order("Client2", LocalDate.now().minusDays(2), "Address2");
        Order o3 = new Order("Client3", LocalDate.now().minusDays(2), "Address3");
        Order o4 = new Order("Client4", LocalDate.now().minusDays(1), "Address4");
        Order o5 = new Order("Client5", LocalDate.now().minusDays(1), "Address5");

        o1.addOrderItem(new OrderItem(p1, 1));
        o2.addOrderItem(new OrderItem(p2, 3));
        o2.addOrderItem(new OrderItem(p3, 2));
        o3.addOrderItem(new OrderItem(p3, 4));
        o4.addOrderItem(new OrderItem(p4, 2));
        o4.addOrderItem(new OrderItem(p5, 15));
        o5.addOrderItem(new OrderItem(p5, 8));

        return args -> {
            productService.create(p1);
            productService.create(p2);
            productService.create(p3);
            productService.create(p4);
            productService.create(p5);

            orderService.create(o1);
            orderService.create(o2);
            orderService.create(o3);
            orderService.create(o4);
            orderService.create(o5);
        };
    }
}
