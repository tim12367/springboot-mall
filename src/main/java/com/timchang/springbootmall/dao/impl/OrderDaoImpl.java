package com.timchang.springbootmall.dao.impl;

import com.timchang.springbootmall.dao.OrderDao;
import com.timchang.springbootmall.dto.OrderQueryParams;
import com.timchang.springbootmall.model.Order;
import com.timchang.springbootmall.model.OrderItem;
import com.timchang.springbootmall.rowMapper.OrderItemRowMapper;
import com.timchang.springbootmall.rowMapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, int totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) \n" +
                "VALUES (:user_id, :total_amount, :created_date, :last_modified_date)";

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("total_amount", totalAmount);
        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        // 法一 每次插入一筆
//        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) \n" +
//                "VALUES (:order_id, :product_id, :quantity, :amount)";

//        for (OrderItem orderItem : orderItemList) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("order_id", orderId);
//            map.put("product_id", orderItem.getProductId());
//            map.put("quantity", orderItem.getQuantity());
//            map.put("amount", orderItem.getAmount());
//
//            namedParameterJdbcTemplate.update(sql, map);
//        }

        // 法二 批次插入 batchUpdate
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) \n" +
                "VALUES (:order_id, :product_id, :quantity, :amount)";
        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            mapSqlParameterSources[i] = new MapSqlParameterSource();
            mapSqlParameterSources[i].addValue("order_id", orderId);
            mapSqlParameterSources[i].addValue("product_id", orderItem.getProductId());
            mapSqlParameterSources[i].addValue("quantity", orderItem.getQuantity());
            mapSqlParameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date\n" +
                "FROM `order` \n" +
                "WHERE order_id = :order_id";
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);

        List<Order> orders = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url\n" +
                "FROM order_item as oi\n" +
                "LEFT JOIN product as p\n" +
                "ON oi.product_id = p.product_id\n" +
                "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, orderQueryParams);

        // 排序
        sql += " ORDER BY created_date DESC";

        // 分頁
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        return orderList;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        if (orderQueryParams.getUserId() != null) {
            sql += " AND user_id = :userId";
            map.put("userId", orderQueryParams.getUserId());
        }

        return sql;
    }
}
