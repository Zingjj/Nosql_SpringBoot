package cn.itcast.springboot.pojo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: Zing
 * @date: 2019/12/4 20:51
 */
public class StatisticStuCnt {

    @Field("_id")
    private Integer cid;
    @Field("stucount")
    private Integer stucnt;
    @Field("cname")
    private String name;
    @Field("avgscore")
    private Double avgscore;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStucnt() {
        return stucnt;
    }

    public void setStucnt(Integer stucnt) {
        this.stucnt = stucnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(Double avgscore) {
        this.avgscore = avgscore;
    }
}
