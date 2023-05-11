package ru.clevertec.ecl.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        Order order = orderMapper.dtoToEntity(orderDto);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.entityToDto(savedOrder);
    }

    @Override
    public List<OrderDto> findAllOrders(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Order> orders = orderRepository.findAll(pageable).getContent();
        return orders.stream()
                .map(order -> orderMapper.entityToDto(order))
                .toList();
    }

    @Override
    public OrderDto findOrderById(long id) {
        OrderDto orderDto = orderRepository.findById(id)
                .map(order -> orderMapper.entityToDto(order))
                .orElseThrow(() -> new NoSuchElementException("order with id " + id + " wasn't found"));
        return orderDto;
    }

    @Override
    public List<OrderDto> findOrdersByUserId(long id) {
        List<Order> orders = orderRepository.findByUserId(id);
        return orders.stream()
                .map(order -> orderMapper.entityToDto(order))
                .toList();
    }

    @Override
    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }
}
