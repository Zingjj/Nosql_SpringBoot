package cn.itcast.springboot.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author: Zing
 * @date: 2019/12/3 16:42
 */
public class Teacher_Course implements Serializable {
    @Id
    private String _id;
    @Field("CID")
    private Integer cid;
    @Field("TID")
    private Integer tid;

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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}
