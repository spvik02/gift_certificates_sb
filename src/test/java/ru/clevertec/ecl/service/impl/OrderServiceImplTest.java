package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderServiceImpl.class})
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Nested
    class SaveOrder {

        @Test
        void checkSaveOrderShouldSetOrderDateField() {
            Order order = TestData.buildOrder();
            order.setId(null);
            OrderDto orderDto = TestData.buildOrderDto();

            when(orderMapper.dtoToEntity(any(OrderDto.class)))
                    .thenReturn(order);
            when(orderRepository.save(any(Order.class)))
                    .thenReturn(order);
            when(orderMapper.entityToDto(any(Order.class)))
                    .thenReturn(orderDto);

            orderService.saveOrder(orderDto);
            verify(orderRepository).save(orderCaptor.capture());
            Order orderCaptorValue = orderCaptor.getValue();

            assertNotNull(orderCaptorValue.getOrderDate());
        }

        @Test
        void checkSaveGiftCertificateInvokeRepositoryMethod() {
            Order order = TestData.buildOrder();
            OrderDto orderDto = TestData.buildOrderDto();

            when(orderMapper.dtoToEntity(any(OrderDto.class)))
                    .thenReturn(order);
            when(orderRepository.save(any(Order.class)))
                    .thenReturn(order);
            when(orderMapper.entityToDto(any(Order.class)))
                    .thenReturn(orderDto);

            orderService.saveOrder(orderDto);

            verify(orderRepository, times(1)).save(any(Order.class));
        }
    }

    @Nested
    class FindAllOrdersPageable {

        @Test
        void checkFindAllOrdersPageableInvokeRepositoryMethod() {
            List<Order> orderList = List.of(TestData.buildOrder());
            Page<Order> orderPage = new PageImpl<>(orderList);

            when(orderRepository.findAll(any(Pageable.class)))
                    .thenReturn(orderPage);
            when(orderMapper.entityToDto(any(Order.class)))
                    .thenReturn(TestData.buildOrderDto());

            orderService.findAllOrdersPageable(Pageable.ofSize(3));

            verify(orderRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class FindOrderById {

        @Test
        void checkFindOrderByIdShouldThrowNoSuchElementException() {
            doReturn(Optional.empty()).when(orderRepository).findById(1L);

            assertThatThrownBy(() -> {
                orderService.findOrderById(1L);
            }).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void checkFindOrderByIdInvokeRepositoryMethod() {
            Order order = TestData.buildOrder();
            when(orderRepository.findById(1L))
                    .thenReturn(Optional.of(order));
            when(orderMapper.entityToDto(any(Order.class)))
                    .thenReturn(TestData.buildOrderDto());

            orderService.findOrderById(1L);

            verify(orderRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class FindOrdersByUserId {

        @Test
        void checkFindOrdersByUserIdInvokeRepositoryMethod() {
            List<Order> orderList = List.of(TestData.buildOrder());

            when(orderRepository.findByUserId(anyLong()))
                    .thenReturn(orderList);
            when(orderMapper.entityToDto(any(Order.class)))
                    .thenReturn(TestData.buildOrderDto());

            orderService.findOrdersByUserId(1L);

            verify(orderRepository, times(1)).findByUserId(1L);
        }
    }

    @Nested
    class DeleteOrderById {
        @Test
        void checkDeleteOrderByIdInvokeRepositoryMethod() {
            doNothing().when(orderRepository).deleteById(anyLong());

            orderService.deleteOrderById(1L);

            verify(orderRepository, times(1)).deleteById(1L);
        }
    }
}
