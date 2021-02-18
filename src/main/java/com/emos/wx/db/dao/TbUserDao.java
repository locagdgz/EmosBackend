package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Set;

@Mapper
public interface TbUserDao {

    int insert(TbUser user);

    boolean haveRootUser();

    Integer searchIdByOpenId(String openId);

    Set<String> searchUserPermissions(int id, int status);

    TbUser searchUserById(int userId);

    HashMap<String, String> searchNameAndDept(int userId);
}