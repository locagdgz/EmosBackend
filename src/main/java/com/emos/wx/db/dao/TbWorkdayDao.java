package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbWorkday;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbWorkdayDao {

    Integer searchTodayIsWorkday();

}