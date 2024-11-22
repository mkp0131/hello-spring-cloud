package kr.lililli.order_service.controller;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.lililli.order_service.dto.OrderDto;
import kr.lililli.order_service.jpa.OrderEntity;
import kr.lililli.order_service.service.OrderService;
import kr.lililli.order_service.vo.ApiResponse;
import kr.lililli.order_service.vo.ResponseOrder;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"", "/"})
    public String getHello() {
        return "It's Working in Order Service";
    }


    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getOrderByUserId(@PathVariable String userId) {
        Iterable<OrderDto> orderList = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(modelMapper.map(v, ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable String userId,
            @RequestBody OrderDto orderDetails) {
        OrderDto createdOrder = orderService.createOrder(orderDetails);
        ResponseOrder responseOrder = modelMapper.map(createdOrder, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseOrder));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderByOrderId(@PathVariable String orderId) {
        OrderDto orderDto = orderService.getOrderByOrderId(orderId);

        ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseOrder));
    }
}
