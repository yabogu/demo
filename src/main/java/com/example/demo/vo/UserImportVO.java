package com.example.demo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;

@Data
public class UserImportVO {

    /**
     * 名称
     */
    @Excel(name="名称")
    private String name;

    /**
     * 年龄
     */
    @Excel(name="年龄")
    private Integer age;

    /**
     * 职位
     */
    @Excel(name="职位")
    private String post;

}
