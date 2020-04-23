package com.example.demo.controller;

import com.example.demo.util.RedisUtil;
import com.example.demo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/save")
    public void saveUser(@RequestBody UserVO userVO) {
        redisUtil.set(userVO.getName(),userVO);
    }

}
