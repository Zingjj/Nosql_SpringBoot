package cn.itcast.springboot.dao.impl;

import cn.itcast.springboot.dao.TCDao;
import cn.itcast.springboot.pojo.Teacher_Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @author: Zing
 * @date: 2019/12/3 16:46
 */

@Component
public class TCDaoImpl implements TCDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Teacher_Course findByCid(Integer cid){
        Query query = new Query();
        Criteria criteria = Criteria.where("cid").is(cid);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, Teacher_Course.class,"teacher_course");
    }

}
