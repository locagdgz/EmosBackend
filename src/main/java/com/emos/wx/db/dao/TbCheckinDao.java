package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbCheckin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TbCheckinDao {

    Integer haveCheckin(Map map);

    void insert(TbCheckin entity);

}