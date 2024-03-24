package com.timchang.springbootmall.service.impl;

import com.timchang.springbootmall.dao.OrderDao;
import com.timchang.springbootmall.dao.ProductDao;
import com.timchang.springbootmall.dao.UserDao;
import com.timchang.springbootmall.dto.BuyItem;
import com.timchang.springbootmall.dto.CreateOrderRequest;
import com.timchang.springbootmall.model.Order;
import com.timchang.springbootmall.model.OrderItem;
import com.timchang.springbootmall.model.Product;
import com.timchang.springbootmall.model.User;
import com.timchang.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional // 確保多資料庫操作rollback
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        log.debug("OrderServiceImpl.createOrder");
        log.debug(createOrderRequest.toString());
        // 檢核是否有user
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("創建訂單失敗! 該User id {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 訂單創建流程
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢核
            if (product == null) {

                log.warn("創建訂單失敗! 商品 {} 不存在", buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {

                log.warn("創建訂單失敗! 商品數量: {} 訂單數量: {}", product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            // 轉換 BuyItem -> OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        // 創建訂單詳情
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }
}
