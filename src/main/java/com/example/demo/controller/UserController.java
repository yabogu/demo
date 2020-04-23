package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.aspect.WebLogger;
import com.example.demo.service.impl.UserService;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public List<UserVO> queryUserList() {
        return userService.queryUserList();
    }

    @GetMapping("by/id")
    public UserVO queryUserById(Long id) {
        return userService.queryUserById(id);
    }

    @WebLogger
    @GetMapping("list/page/{current}/{size}")
    public IPage queryUserPageList(
            Page page, UserVO userVO) {
        return userService.queryUserPageList(page,userVO);
    }


    @PostMapping("add")
    public String addUser(@RequestBody UserVO userVO) {
        return userService.addUser(userVO);
    }

    /**
     * 导出
     *
     * @param response
     */
    @GetMapping(value = "/export")
    public void exportExcel(HttpServletResponse response,UserVO userVO)throws Exception {
        userService.exportExcel(response,userVO);
    }

    /**
     * 导出
     *
     * @param response
     */
    @GetMapping(value = "/downLoadExcel")
    public void downLoadExcel(HttpServletResponse response)throws Exception {
        userService.downLoadExcel(response);
    }

    /**
     * 导入
     */
    @PostMapping("/importUser")
    public void importUser(@RequestParam("file") MultipartFile file)throws Exception {
        userService.importUser(file);
    }
}
