package cn.itcast.springboot.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author: Zing
 * @date: 2019/12/1 16:34
 */
public class Student_Course implements Serializable {
    @Id
    private String _id;
    @Field("SID")
    private Long sid;
    @Field("CID")
    private Integer cid;
    @Field("SCORE")
    private Integer score;
    @Field("TID")
    private Integer tid;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}
