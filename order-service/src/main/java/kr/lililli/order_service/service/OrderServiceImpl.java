package kr.lililli.order_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import kr.lililli.order_service.dto.OrderDto;
import kr.lililli.order_service.exception.NotFoundException;
import kr.lililli.order_service.jpa.OrderEntity;
import kr.lililli.order_service.jpa.OrderRepository;


@Service
public class OrderServiceImpl implements OrderService {


    OrderRepository orderRepository;
    ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDetail) {
        orderDetail.setOrderId(UUID.randomUUID().toString());
        orderDetail.setTotalPrice(orderDetail.getQty() * orderDetail.getUnitPrice());
        OrderEntity orderEntity = modelMapper.map(orderDetail, OrderEntity.class);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        OrderDto returnValue = modelMapper.map(savedOrder, OrderDto.class);

        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        if (orderEntity == null) {
            throw new NotFoundException("주문 정보를 찾을 수 없습니다.");
        }

        OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
        return returnValue;
    }

    @Override
    public List<OrderDto> getOrdersByUserId(String userId) {
        Iterable<OrderEntity> orderEntities = orderRepository.findByUserId(userId);
        List<OrderDto> returnValue = new ArrayList<>();
        orderEntities.forEach(v -> {
            returnValue.add(modelMapper.map(v, OrderDto.class));
        });
        return returnValue;
    }

}
