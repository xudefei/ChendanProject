package com.example.administrator.chendanproject.Entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/3/8.
 * 创建数据库的表
 */
@Table(name = "UserTable")
public class User {
    /**
     * name:名称
     * isId：是否是主键
     * autoGen：是否自增长
     * property：约束 ，不能为空
     * */
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;

    @Column(name = "sex")
    private String sex;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;
    //必须声明这个默认构造方法，否则表无法创建
    public User(){

    }
    public User(int id,String sex,String name,String phone){
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User对象{"+"id = "+id+",sex = "+sex+",name = "+name+",phone = "+phone;
    }
}
