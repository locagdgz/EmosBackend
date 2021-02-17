package com.emos.wx.db.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbHolidaysDao {

    Integer searchTodayIsHolidays();

}