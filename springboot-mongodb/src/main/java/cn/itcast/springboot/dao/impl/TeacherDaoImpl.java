package cn.itcast.springboot.dao.impl;

import cn.itcast.springboot.dao.TeacherDao;
import cn.itcast.springboot.pojo.PageModel;
import cn.itcast.springboot.pojo.Teacher;
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
 * @date: 2019/12/1 12:48
 */
@Component
public class TeacherDaoImpl implements TeacherDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insert(Teacher teacher) {
        mongoTemplate.insert(teacher);
        return true;
    }

    @Override
    public boolean update(Teacher teacher) {
        //创建更新条件
        Query query = new Query();
        Criteria criteria = Criteria.where("tid").is(teacher.getTid());
        query.addCriteria(criteria);

        //创建更新对象
        Update update = new Update();
        update.set("tid",teacher.getTid());
        update.set("name",teacher.getName());
        update.set("sex",teacher.getSex());
        update.set("age",teacher.getAge());
        update.set("dname",teacher.getDname());
        //更新所有符合条件的记录
        mongoTemplate.updateMulti(query, update, Teacher.class);
        if (mongoTemplate.updateMulti(query, update, Teacher.class).getModifiedCount()==0){
            System.out.println("更新失败");
            return false;
        }else{
            System.out.println("更新成功，"+mongoTemplate.updateMulti(query, update, Teacher.class).getModifiedCount()+"条数据被更新");
            return true;
        }
    }

    @Override
    public boolean deleteById(Integer tid) {
        Query query = new Query();
        Criteria criteria = Criteria.where("tid").is(tid);
        query.addCriteria(criteria);
        mongoTemplate.remove(query,Teacher.class);
        if(mongoTemplate.remove(query,Teacher.class).getDeletedCount()==0) return false;
        else return true;
    }

    @Override
    public Teacher findById(Integer tid) {
        Query query = new Query();
        Criteria criteria = Criteria.where("tid").is(tid);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, Teacher.class);
    }

    @Override
    public List<Teacher> findByName(String name) {
        Query query = new Query();
        //模糊查询，不区分大小写
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("name").regex(pattern);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Teacher.class);
    }

    @Override
    public Page<Teacher> paginationQuery(Integer pageNum) {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm=new PageModel();
        Query query=new Query();


        // 设置当前页?
        pm.setPagenumber(pageNum);
        // 每页条数
        pm.setPagesize(10);
        pm.setSort(Sort.by(Sort.Direction.ASC, "_id"));
        pageable.setPage(pm);
        Long cnt = mongoTemplate.count(query,Teacher.class);
        List<Teacher> list = mongoTemplate.find(query.with(pageable), Teacher.class);
        Page<Teacher> pageList=new PageImpl<Teacher>(list,pageable,cnt);
        return pageList;
    }
}
