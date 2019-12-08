package cn.itcast.springboot.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author: Zing
 * @date: 2019/12/1 15:49
 */
public class Course implements Serializable {
    @Id
    private String _id;
    @Field("CID")
    private Integer cid;
    @Field("NAME")
    private String name;
    @Field("FCID")
    private Integer fcid;
    @Field("CREDIT")
    private Integer credit;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFcid() {
        return fcid;
    }

    public void setFcid(Integer fcid) {
        this.fcid = fcid;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
