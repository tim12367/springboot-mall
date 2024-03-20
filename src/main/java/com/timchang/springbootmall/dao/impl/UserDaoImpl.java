package com.timchang.springbootmall.dao.impl;

import com.timchang.springbootmall.dao.UserDao;
import com.timchang.springbootmall.dto.UserRegisterRequest;
import com.timchang.springbootmall.model.User;
import com.timchang.springbootmall.rowMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date)\n" +
                "VALUES (:email, :password, :created_date, :last_modified_date)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", userRegisterRequest.getEmail());
        paramMap.put("password", userRegisterRequest.getPassword());
        Date now = new Date();
        paramMap.put("created_date", now);
        paramMap.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT\n" +
                "    user_id, email, password, created_date, last_modified_date\n" +
                "FROM user\n" +
                "WHERE user_id=:userId";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        List<User> result = namedParameterJdbcTemplate.query(sql, paramMap, new UserRowMapper());

        if (result.size()>0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
