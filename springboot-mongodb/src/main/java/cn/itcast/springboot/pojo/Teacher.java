package cn.itcast.springboot.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author: Zing
 * @date: 2019/12/1 12:49
 */
public class Teacher implements Serializable {
    @Id
    private String _id;
    @Field("TID")
    private Integer tid;
    @Field("NAME")
    private String name;
    @Field("SEX")
    private String sex;
    @Field("AGE")
    private Integer age;
    @Field("DNAME")
    private String dname;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
