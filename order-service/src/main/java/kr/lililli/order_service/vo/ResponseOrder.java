package kr.lililli.order_service.vo;

import java.time.LocalDateTime;
import lombok.Data;


@Data
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private String orderId;
}
