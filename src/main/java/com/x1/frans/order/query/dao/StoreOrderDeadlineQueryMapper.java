package com.x1.frans.order.query.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalTime;

@Mapper
public interface StoreOrderDeadlineQueryMapper {
    @Select("SELECT order_deadline_at FROM store_order_deadline ORDER BY id DESC LIMIT 1")
    LocalTime findLatestDeadline();
}
