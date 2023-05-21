package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OrderServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void checkSaveTagShouldReturnTagWithNotNullId() {
        OrderDto orderDto = TestData.buildOrderDto();
        orderDto.setId(null);

        OrderDto actualOrder = orderService.saveOrder(orderDto);

        assertThat(actualOrder.getId()).isNotNull();
    }

    @Test
    void checkFindAllOrdersPageableShouldReturn3() {
        int expectedCount = 3;

        List<OrderDto> actualOrders = orderService.findAllOrdersPageable(Pageable.ofSize(5));

        assertThat(actualOrders).hasSize(expectedCount);
    }

    @Test
    void checkFindAllOrdersPageableShouldReturn2() {
        int expectedCount = 2;

        List<OrderDto> actualOrders = orderService.findAllOrdersPageable(Pageable.ofSize(2));

        assertThat(actualOrders).hasSize(expectedCount);
    }

    @Test
    void checkFindOrderByIdShouldReturnOrderWithId1() {
        long expectedId = 1;

        OrderDto actualOrder = orderService.findOrderById(1L);

        assertThat(actualOrder.getId()).isEqualTo(expectedId);
    }

    @Test
    void checkOrdersByUserIdShouldReturn2() {
        int expectedCount = 2;

        List<OrderDto> actualOrders = orderService.findOrdersByUserId(1L);

        assertThat(actualOrders).hasSize(expectedCount);
    }

    @Test
    void checkDeleteOrderByIdShouldNotFindOrderByDeletedId() {
        long expectedId = 1;

        orderService.deleteOrderById(expectedId);
        entityManager.flush();

        assertThatThrownBy(() -> {
            orderService.findOrderById(expectedId);
        }).isInstanceOf(NoSuchElementException.class);
    }
}
