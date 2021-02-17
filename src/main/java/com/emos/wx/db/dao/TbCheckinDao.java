package com.emos.wx.db.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TbCheckinDao {

    Integer haveCheckin(Map map);

}