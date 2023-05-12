package ru.clevertec.ecl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.service.OrderService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    /**
     * Creates new order with provided data
     *
     * @param orderDto order data
     * @return ResponseEntity with saved order
     */
    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto orderDto) {
        OrderDto savedOrder = orderService.saveOrder(orderDto);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    /**
     * Returns list of orders within the specified range
     *
     * @param page     value in range [0..n]
     * @param pageSize the number of orders per page
     * @return ResponseEntity with list of orders
     */
    @GetMapping
    public ResponseEntity<List<OrderDto>> findAllOrders(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        List<OrderDto> orders = orderService.findAllOrders(page, pageSize);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Returns order with provided id
     *
     * @param id order id
     * @return ResponseEntity with order with provided id
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable("id") long id) {
        OrderDto order = orderService.findOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Returns list of orders of user with specified id
     *
     * @param id uder id
     * @return ResponseEntity with list of orders
     */
    @GetMapping("user/{id}")
    public ResponseEntity<List<OrderDto>> findOrdersByUserId(@PathVariable("id") long id) {
        List<OrderDto> orders = orderService.findOrdersByUserId(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Deletes passed GiftCertificate. Body can contain only id
     *
     * @param id order id for deletion
     * @return empty ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
