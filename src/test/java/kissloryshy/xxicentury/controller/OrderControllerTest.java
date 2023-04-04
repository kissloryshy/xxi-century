package kissloryshy.xxicentury.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kissloryshy.xxicentury.entity.Order;
import kissloryshy.xxicentury.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getAll() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>()));
        orders.add(new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>()));

        when(orderService.getAll()).thenReturn(orders);

        mockMvc.perform(get("/orders")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].client").value("Ivanov"));

        verify(orderService).getAll();
    }

    @Test
    void getById() throws Exception {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());

        when(orderService.getById(o1.getId())).thenReturn(Optional.of(o1));

        mockMvc.perform(get("/orders/" + o1.getId())).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(orderService).getById(o1.getId());
    }

    @Test
    void create() throws Exception {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());

        when(orderService.create(o1)).thenReturn(o1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String orderJson = objectMapper.writeValueAsString(o1);
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson)).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(content().json(orderJson));

        verify(orderService).create(o1);
    }

    @Test
    void update() throws Exception {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());

        when(orderService.update(o1.getId(), o1)).thenReturn(o1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc.perform(put("/orders/" + o1.getId())
                        .content(objectMapper.writeValueAsString(o1))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());

        verify(orderService, times(1)).update(o1.getId(), o1);
    }

    @Test
    void deleteOrder() throws Exception {
        Order o1 = new Order(1L, "Ivanov", LocalDate.now(), "Moscow", new ArrayList<>());

        doNothing().when(orderService).delete(o1.getId());

        mockMvc.perform(delete("/orders/" + o1.getId())).andDo(print())
                .andExpect(status().isOk());

        verify(orderService).delete(o1.getId());
    }
}