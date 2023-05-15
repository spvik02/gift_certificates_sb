package ru.clevertec.ecl.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.repository.OrderRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {

}
