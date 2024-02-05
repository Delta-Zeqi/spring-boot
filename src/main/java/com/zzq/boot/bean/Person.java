package com.zzq.boot.bean;

import lombok.Data;

import java.util.Date;

/**
 *     姓名：<input name="userName" value="zhangsan">
 *     年龄：<input name="age" value="18">
 *     生日：<input name="birth" value="2024/02/04">
 *     宠物姓名：<input name="pet.name" value="tom">
 *     宠物年龄：<input name="pet.age" value="5">
 */
@Data
public class Person {
    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;

}
