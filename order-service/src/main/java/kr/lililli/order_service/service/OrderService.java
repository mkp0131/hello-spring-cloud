package kr.lililli.order_service.service;

import java.util.List;
import kr.lililli.order_service.dto.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetail);

    OrderDto getOrderByOrderId(String orderId);

    List<OrderDto> getOrdersByUserId(String userId);

}
