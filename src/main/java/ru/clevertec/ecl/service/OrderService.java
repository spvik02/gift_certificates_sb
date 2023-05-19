package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto saveOrder(OrderDto orderDto);

    List<OrderDto> findAllOrdersPageable(Pageable pageable);

    OrderDto findOrderById(long id);

    List<OrderDto> findOrdersByUserId(long id);

    void deleteOrderById(long id);
}
