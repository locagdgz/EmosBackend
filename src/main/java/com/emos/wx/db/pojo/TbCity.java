package com.emos.wx.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_config
 * @author 
 */
@Data
public class TbCity implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 拼音简称
     */
    private String code;

    private static final long serialVersionUID = 1L;
}