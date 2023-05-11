package ru.clevertec.ecl.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.repository.OrderRepository;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {

}
