package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto saveOrder(OrderDto orderDto);

    List<OrderDto> findAllOrders(int page, int pageSize);

    OrderDto findOrderById(long id);

    List<OrderDto> findOrdersByUserId(long id);

    void deleteOrderById(long id);
}
