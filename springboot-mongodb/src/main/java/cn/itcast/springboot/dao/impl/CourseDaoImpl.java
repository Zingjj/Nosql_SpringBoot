package cn.itcast.springboot.dao.impl;

import cn.itcast.springboot.dao.CourseDao;
import cn.itcast.springboot.pojo.Course;
import cn.itcast.springboot.pojo.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: Zing
 * @date: 2019/12/1 16:01
 */
@Component
public class CourseDaoImpl implements CourseDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insert(Course course) {
        mongoTemplate.insert(course);
        return true;
    }

    @Override
    public boolean update(Course course) {
        //创建更新条件
        Query query = new Query();
        Criteria criteria = Criteria.where("cid").is(course.getCid());
        query.addCriteria(criteria);

        //创建更新对象
        Update update = new Update();
        update.set("cid",course.getCid());
        update.set("name",course.getName());
        update.set("fcid",course.getFcid());
        update.set("credit",course.getCredit());
        //更新所有符合条件的记录
        mongoTemplate.updateMulti(query, update, Course.class);
        if (mongoTemplate.updateMulti(query, update, Course.class).getModifiedCount()==0){
            System.out.println("更新失败");
            return false;
        }else{
            System.out.println("更新成功，"+mongoTemplate.updateMulti(query, update, Course.class).getModifiedCount()+"条数据被更新");
            return true;
        }
    }

    @Override
    public boolean deleteById(Integer cid) {
        Query query = new Query();
        Criteria criteria = Criteria.where("cid").is(cid);
        query.addCriteria(criteria);
        mongoTemplate.remove(query,Course.class);
        if(mongoTemplate.remove(query,Course.class).getDeletedCount()==0) return false;
        else return true;
    }


    @Override
    public Course findById(Integer cid) {
        Query query = new Query();
        Criteria criteria = Criteria.where("cid").is(cid);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, Course.class);
    }

    @Override
    public List<Course> findByName(String name) {
        Query query = new Query();
        //模糊查询，不区分大小写
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("name").regex(pattern);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Course.class);
    }

    @Override
    public Page<Course> paginationQuery(Integer pageNum) {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm=new PageModel();
        Query query=new Query();

        // 设置当前页?
        pm.setPagenumber(pageNum);
        // 每页条数
        pm.setPagesize(10);
        pm.setSort(Sort.by(Sort.Direction.DESC, "cid"));
        pageable.setPage(pm);
        Long cnt = mongoTemplate.count(query,Course.class);
        List<Course> list = mongoTemplate.find(query.with(pageable), Course.class);
        Page<Course> pageList=new PageImpl<Course>(list,pageable,cnt);
        return pageList;
    }

    @Override
    public List<Course> search(){
        return mongoTemplate.findAll(Course.class);
    }
}
