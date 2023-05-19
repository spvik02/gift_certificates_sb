package ru.clevertec.ecl.model.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Test
    void checkDtoToEntity() {
        OrderDto dto = TestData.buildOrderDto();
        Order expectedOrder = TestData.buildOrder();

        Order actualOrder = mapper.dtoToEntity(dto);

        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    void checkEntityToDto() {
        OrderDto expectedDto = TestData.buildOrderDto();
        Order order = TestData.buildOrder();

        OrderDto actualDto = mapper.entityToDto(order);

        assertThat(actualDto).isEqualTo(expectedDto);
    }
}
