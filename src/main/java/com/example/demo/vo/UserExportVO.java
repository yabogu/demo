package com.example.demo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserExportVO {

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
