package com.example.demo.controller;

import com.example.demo.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("query")
    public UserVO queryUser() {
        UserVO userVO = new UserVO();
        userVO.setName("张三");
        userVO.setAge(11);
        return userVO;
    }

}
