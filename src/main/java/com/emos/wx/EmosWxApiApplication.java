package com.emos.wx;

import cn.hutool.core.util.StrUtil;
import com.emos.wx.common.constants.SystemConstants;
import com.emos.wx.db.dao.SysConfigDao;
import com.emos.wx.db.pojo.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

@SpringBootApplication
@ServletComponentScan
@Slf4j
public class EmosWxApiApplication {

    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private SystemConstants systemConstants;


    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<SysConfig> sysConfigList = sysConfigDao.selectAllParam();
        sysConfigList.forEach(sysConfig -> {
            String key = sysConfig.getParamKey();
            key = StrUtil.toCamelCase(key);
            String value = sysConfig.getParamValue();

            try {
                Field field = systemConstants.getClass().getDeclaredField(key);
                field.set(systemConstants,value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("SystemConstants反射注入异常",e);
            }
        });

        System.out.println(systemConstants);

    }

}
