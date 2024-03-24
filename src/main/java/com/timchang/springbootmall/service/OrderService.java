package com.timchang.springbootmall.service;

import com.timchang.springbootmall.dto.CreateOrderRequest;
import com.timchang.springbootmall.dto.OrderQueryParams;
import com.timchang.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
