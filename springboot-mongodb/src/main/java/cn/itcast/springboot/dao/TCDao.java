package cn.itcast.springboot.dao;

import cn.itcast.springboot.pojo.Teacher_Course;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author: Zing
 * @date: 2019/12/3 16:44
 */
public interface TCDao {
    Teacher_Course findByCid(Integer cid);
}
