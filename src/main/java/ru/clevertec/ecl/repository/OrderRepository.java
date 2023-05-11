package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Page<Order> findAll(Pageable pageable);

    Optional<Order> findById(long id);

    List<Order> findByUserId(long id);

    void deleteById(long id);
}
