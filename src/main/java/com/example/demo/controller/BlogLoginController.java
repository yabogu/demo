package com.example.demo.controller;

import com.example.demo.vo.LoginInfoVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogLoginController {


    @PostMapping("login")
    public String login(@RequestBody LoginInfoVO loginInfoVO) {
        return "success";
    }

}
