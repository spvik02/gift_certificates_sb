package ru.clevertec.ecl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Nested
    class SaveOrder {

        @Test
        void checkSaveOrderValidTest() throws Exception {
            OrderDto orderDto = TestData.buildOrderDto();
            when(orderService.saveOrder(orderDto))
                    .thenReturn(orderDto);

            mockMvc.perform(post("/api/orders")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(orderDto)));
        }
    }

    @Nested
    class FindAllOrders {

        @Test
        void checkFindAllOrdersValidTest() throws Exception {
            List<OrderDto> orderDtoList = List.of(TestData.buildOrderDto());
            when(orderService.findAllOrdersPageable(any(Pageable.class)))
                    .thenReturn(orderDtoList);

            mockMvc.perform(get("/api/orders")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(orderDtoList)));
        }

        @Test
        void checkFindAllOrdersWithInvalidParamsWillNotFail() throws Exception {
            List<OrderDto> orderDtoList = List.of(TestData.buildOrderDto());
            when(orderService.findAllOrdersPageable(any(Pageable.class)))
                    .thenReturn(orderDtoList);

            mockMvc.perform(get("/api/orders")
                            .param("size", "five")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(orderDtoList)));
        }
    }

    @Nested
    class FindOrderById {

        @Test
        void checkFindOrderByIdValidTest() throws Exception {
            long id = 1L;
            OrderDto orderDto = TestData.buildOrderDto();
            when(orderService.findOrderById(id))
                    .thenReturn(orderDto);

            mockMvc.perform(get("/api/orders/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(orderDto)));
        }

        @Test
        void checkFindOrderByIdWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(get("/api/orders/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class FindOrdersByUserId {

        @Test
        void checkFindOrdersByUserIdValidTest() throws Exception {
            List<OrderDto> orderDtoList = List.of(TestData.buildOrderDto());
            when(orderService.findOrdersByUserId(anyLong()))
                    .thenReturn(orderDtoList);

            mockMvc.perform(get("/api/orders/user/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(orderDtoList)));
        }

        @Test
        void checkFindOrdersByUserIdWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(get("/api/orders/user/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class DeleteOrderById {

        @Test
        void checkDeleteOrderByIdValidTest() throws Exception {

            mockMvc.perform(delete("/api/orders/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void checkDeleteOrderByIdWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(delete("/api/orders/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
