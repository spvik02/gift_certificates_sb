package ru.clevertec.ecl.model.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order dtoToEntity(OrderDto orderDto);

    OrderDto entityToDto(Order order);
}
