package com.timchang.springbootmall.controller;

import com.timchang.springbootmall.dto.CreateOrderRequest;
import com.timchang.springbootmall.dto.OrderQueryParams;
import com.timchang.springbootmall.model.Order;
import com.timchang.springbootmall.service.OrderService;
import com.timchang.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // 取得 orderList
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // 取的order總數
        Integer count = orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Order> orderPage = new Page<>();
        orderPage.setLimit(limit);
        orderPage.setOffset(offset);
        orderPage.setTotal(count);
        orderPage.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(orderPage);
    }

    @PostMapping("/users/{userId}/orders") // 訂單為帳號附屬功能 所以在users/ 下面
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
