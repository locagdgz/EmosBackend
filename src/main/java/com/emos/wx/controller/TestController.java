package com.emos.wx.controller;

import com.emos.wx.common.util.R;
import com.emos.wx.controller.vo.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api("测试WEB接口")
public class TestController {


    @PostMapping("/hello")
    @ApiOperation("一个简单的测试接口")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form) {
        return R.ok(form.getName());
    }

}
