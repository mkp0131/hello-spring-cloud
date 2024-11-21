package kr.lililli.order_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
public class OrderController {

    @GetMapping({"", "/"})
    public String getHello() {
        return "It's Working in Order Service";
    }

}
