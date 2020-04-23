package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
* Created by Mybatis Generator 2020/03/18
*/
@Data
@TableName(value = "user")
@Slf4j
public class UserDO implements Serializable {
    /**
     * 主键id
     */
     @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 职位
     */
    @TableField(value = "post")
    private String post;

    @TableField(value = "modify_time")
    private Date modifyTime;

    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "name";

    public static final String COL_AGE = "age";

    public static final String COL_POST = "post";

    public static final String COL_MODIFY_TIME = "modify_time";

    public static final String COL_CREATE_TIME = "create_time";
}