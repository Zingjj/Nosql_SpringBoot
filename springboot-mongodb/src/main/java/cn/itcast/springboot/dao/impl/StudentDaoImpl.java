package cn.itcast.springboot.dao.impl;

import cn.itcast.springboot.dao.StudentDao;
import cn.itcast.springboot.pojo.PageModel;
import cn.itcast.springboot.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author: Zing
 * @date: 2019/11/30 13:23
 */
@Component
public class StudentDaoImpl implements StudentDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insert(Student student) {
        mongoTemplate.insert(student);
        return true;
    }

    @Override
    public boolean update(Student student) {
        //创建更新条件
        Query query = new Query();
        Criteria criteria = Criteria.where("sid").is(student.getSid());
        query.addCriteria(criteria);

        //创建更新对象
        Update update = new Update();
        update.set("sid", student.getSid());
        update.set("name", student.getName());
        update.set("sex", student.getSex());
        update.set("age", student.getAge());
        update.set("birthday", student.getBirthday());
        update.set("dname", student.getDname());
        update.set("stuClass", student.getStuClass());

        //更新所有符合条件的记录
        mongoTemplate.updateMulti(query, update, Student.class);
        if (mongoTemplate.updateMulti(query, update, Student.class).getModifiedCount()==0){
            System.out.println("更新失败");
            return false;
        }else{
            System.out.println("更新成功，"+mongoTemplate.updateMulti(query, update, Student.class).getModifiedCount()+"条数据被更新");
            return true;
        }
    }

    @Override
    public boolean deleteById(Long sid) {
        //创建删除条件
        Query query = new Query();
        Criteria criteria = Criteria.where("sid").is(sid);
        query.addCriteria(criteria);
        mongoTemplate.remove(query, Student.class);
        if (mongoTemplate.remove(query, Student.class).getDeletedCount()==0) return false;
        else return true;
    }
    @Override
    public boolean deleteByName(String name) {
        //创建删除条件
        Query query = new Query();
        Criteria criteria = Criteria.where("name").is(name);
        query.addCriteria(criteria);
        mongoTemplate.remove(query, Student.class);
        if (mongoTemplate.remove(query, Student.class).getDeletedCount()==0) return false;
        else return true;
    }

    @Override
    public Student findById(Long sid) {
        Query query = new Query();
        Criteria criteria = Criteria.where("sid").is(sid);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, Student.class);
    }

    @Override
    public List<Student> findByName(String name) {
        Query query = new Query();
        //模糊查询，不区分大小写
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("name").regex(pattern);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Student.class);
    }

    @Override
    public Page<Student> paginationQuery(Integer pageNum){
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm=new PageModel();
        Query query=new Query();

        // 设置当前页?
        pm.setPagenumber(pageNum);
        // 每页条数
        pm.setPagesize(10);
        pm.setSort(Sort.by(Sort.Direction.ASC, "_id"));
        pageable.setPage(pm);
        Long cnt = mongoTemplate.count(query,Student.class);
        List<Student> list = mongoTemplate.find(query.with(pageable), Student.class);
        Page<Student> pageList=new PageImpl<Student>(list,pageable,cnt);
        return pageList;
      //  /return null;
    }
}
