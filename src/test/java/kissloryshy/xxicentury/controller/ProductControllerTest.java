package kissloryshy.xxicentury.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kissloryshy.xxicentury.entity.Product;
import kissloryshy.xxicentury.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getAll() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Mon", 10, false));
        products.add(new Product(2L, "Ph", 23, false));

        when(productService.getAll()).thenReturn(products);

        mockMvc.perform(get("/products")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Mon"));

        verify(productService).getAll();
    }

    @Test
    void getById() throws Exception {
        Product p1 = new Product(1L, "Mon", 10, false);

        when(productService.getById(p1.getId())).thenReturn(Optional.of(p1));

        mockMvc.perform(get("/products/" + p1.getId())).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(productService).getById(p1.getId());
    }

    @Test
    void create() throws Exception {
        Product p1 = new Product(1L, "Mon", 10, false);

        when(productService.create(p1)).thenReturn(p1);

        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(p1);
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mon"))
                .andExpect(content().json(productJson));

        verify(productService).create(p1);
    }

    @Test
    void update() throws Exception {
        Product p1 = new Product(1L, "Mon", 10, false);

        when(productService.update(p1.getId(), p1)).thenReturn(p1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc.perform(put("/products/" + p1.getId())
                        .content(objectMapper.writeValueAsString(p1))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());

        verify(productService, times(1)).update(p1.getId(), p1);
    }

    @Test
    void deleteProduct() throws Exception {
        Product p1 = new Product(1L, "Mon", 10, false);

        doNothing().when(productService).delete(p1.getId());

        mockMvc.perform(delete("/products/" + p1.getId())).andDo(print())
                .andExpect(status().isOk());

        verify(productService).delete(p1.getId());
    }
}