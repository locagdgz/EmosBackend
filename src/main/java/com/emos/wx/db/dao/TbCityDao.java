package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbCity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbCityDao {

    String searchCode(String city);

}