package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbFaceModelDao {

    String searchFaceModel(int userId);
    void insert(TbFaceModel faceModelEntity);
    int deleteFaceModel(int userId);

}