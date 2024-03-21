package com.timchang.springbootmall.service;

import com.timchang.springbootmall.dto.CreateOrderRequest;
import com.timchang.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
