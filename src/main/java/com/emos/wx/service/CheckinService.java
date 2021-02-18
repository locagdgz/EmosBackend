package com.emos.wx.service;

import com.emos.wx.controller.vo.CheckinForm;

import java.util.HashMap;

public interface CheckinService {
    String validCanCheckin(int userId, String date);
    void checkin(HashMap param);

    void createFaceModel(int userId, String path);
}
