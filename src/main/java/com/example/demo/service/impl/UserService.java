package com.example.demo.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.UserDO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.ExcelUtils;
import com.example.demo.vo.UserExportVO;
import com.example.demo.vo.UserImportVO;
import com.example.demo.vo.UserVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, UserDO> {

    public List<UserVO> queryUserList() {
        List<UserDO> list = this.list();
        List<UserVO> userList = Lists.newArrayList();
        if(CollUtil.isNotEmpty(list)) {
            list.forEach(userDO -> {
                UserVO userVO = new UserVO();
                BeanUtil.copyProperties(userDO,userVO);
                userList.add(userVO);
            });
        }
        return userList;
    }

    public String addUser(UserVO userVO) {
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(userVO,userDO);
        this.save(userDO);
        return "success";
    }

    public void exportExcel(HttpServletResponse response, UserVO userVO)throws Exception {
        LambdaQueryWrapper<UserDO> lambdaQueryWrapper = buildUserCondition(userVO);
        List<UserDO> userDOS = this.list(lambdaQueryWrapper);
        List<UserExportVO> userExports = Lists.newArrayList();
        userDOS.forEach(userDO->{
            UserExportVO userExportVO = new UserExportVO();
            userExportVO.setName(userDO.getName());
            userExportVO.setAge(userDO.getAge());
            userExportVO.setPost(userDO.getPost());
            userExports.add(userExportVO);
        });
        ExcelUtils.exportExcel(userExports,"用户信息导出","用户信息",
                UserExportVO.class,"用户信息导出",true,response);

    }

    private LambdaQueryWrapper<UserDO> buildUserCondition(UserVO userVO) {
        LambdaQueryWrapper<UserDO> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(ObjectUtil.isNotNull(userVO)) {
            if(StrUtil.isNotBlank(userVO.getName())) {
                lambdaQueryWrapper.like(UserDO::getName,userVO.getName());
            }
            if(ObjectUtil.isNotNull(userVO.getAge())) {
                lambdaQueryWrapper.eq(UserDO::getAge,userVO.getAge());
            }
        }
        return lambdaQueryWrapper;
    }

    public void downLoadExcel(HttpServletResponse response)throws Exception {
        List<UserImportVO> userExports = Lists.newArrayList();
        ExcelUtils.exportExcel(userExports,"用户信息","用户信息",
                UserImportVO.class,"用户信息导入模板",true,response);
    }

    @Transactional(rollbackFor = Exception.class)
    public void importUser(MultipartFile file) throws Exception{
        ImportParams params = new ImportParams();
        //设置表头的行数
        params.setHeadRows(1);
        List<UserImportVO> userImportVOS = ExcelImportUtil.importExcel(file.getInputStream(), UserImportVO.class, params);
        List<UserDO> userDOs = Lists.newArrayList();
        userImportVOS.forEach(userImportVO -> {
            UserDO userDO = new UserDO();
            userDO.setName(userImportVO.getName());
            userDO.setAge(userImportVO.getAge());
            userDO.setPost(userImportVO.getPost());
            userDOs.add(userDO);
        });
        this.saveBatch(userDOs);
    }

    public IPage queryUserPageList(Page page, UserVO userVO) {
        LambdaQueryWrapper<UserDO> lambdaQueryWrapper = buildUserCondition(userVO);
        Page<UserDO> pageResult = this.page(page,lambdaQueryWrapper);
        return pageResult;
    }

    @Cacheable(value="userVO", key="#id")
    public UserVO queryUserById(Long id) {
        log.info("--id:---",id);
        UserDO userDO = this.getById(id);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userDO,userVO);
        return userVO;
    }
}
