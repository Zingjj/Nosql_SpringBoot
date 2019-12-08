package cn.itcast.springboot.dao.impl;

import cn.itcast.springboot.dao.CourseDao;
import cn.itcast.springboot.dao.SCDao;
import cn.itcast.springboot.dao.TCDao;
import cn.itcast.springboot.pojo.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.swing.text.Document;
import java.awt.image.LookupOp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 16:48
 */
@Component
public class SCDaoImpl implements SCDao {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TCDao tcDao;
    @Autowired
    private CourseDao courseDao;


    @Override
    public boolean insert(Long sid,String cids) {
        String[] cidList= cids.split(";");
        List<Student_Course> scList= new ArrayList<>();
        for (int i=0;i<cidList.length;i++){
            if (cidList[i] == null){continue;}
//            System.out.println(cidList[i]);
            Student_Course sc = new Student_Course();
            sc.setSid(sid);
            sc.setCid(Integer.parseInt(cidList[i]));
            System.out.println(sc.getCid());
            Teacher_Course tc = tcDao.findByCid(Integer.parseInt(cidList[i]));
            // 可能存在没有安排老师教的课
            if(tc!=null){
                sc.setTid(tc.getTid());
            }
            scList.add(sc);
        }
        // 初始化一个数组
        Collection result = mongoTemplate.insert(scList,"student_course");
        if (result.size() == 0) {
            System.out.println("插入失败");
            return false;
        } else {
            System.out.println("新增" + result.size() + "条数据");
            return true;
        }
    }

    @Override
    public boolean update(Student_Course sc) {
        //创建多条件的更新
        Query query = new Query();
        Criteria criteria = Criteria.where("sid").is(sc.getSid()).and("cid").is(sc.getCid());
        query.addCriteria(criteria);
        //创建更新对象
        Update update = new Update();
        update.set("sid", sc.getSid());
        update.set("cid", sc.getCid());
        update.set("score", sc.getScore());
        update.set("tid", sc.getTid());
        //更新所有符合条件的记录
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Student_Course.class);
        if (updateResult.getModifiedCount() == 0) {
            System.out.println("更新失败");
            return false;
        } else {
            System.out.println("更新成功，" + mongoTemplate.updateMulti(query, update,"student_course").getModifiedCount() + "条数据被更新");
            return true;
        }
    }

    @Override
    public boolean deleteBySidCid(Long sid, Integer cid) {
        System.out.println(sid + "  " + cid);
        Query query = new Query();
        Criteria criteria = Criteria.where("sid").is(sid).and("cid").is(cid);
        query.addCriteria(criteria);
        DeleteResult deleteResult = mongoTemplate.remove(query, Student_Course.class, "student_course");
        if (deleteResult.getDeletedCount() == 0) {
            System.out.println("删除失败");
            return false;
        } else {
            System.out.println("删除成功");
            return true;
        }
    }

    @Override
    public Page<Student_Course> findBySid(Long sid,Integer pageNum) {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm = new PageModel();
        Query query = new Query();
        Criteria criteria = Criteria.where("sid").is(sid);
        query.addCriteria(criteria);

        pm.setPagenumber(pageNum);
        pm.setPagesize(10);
        pm.setSort(Sort.by(Sort.Direction.ASC, "cid"));
        pageable.setPage(pm);
        Long cnt = mongoTemplate.count(query, Student_Course.class, "student_course");
        List<Student_Course> list = mongoTemplate.find(query.with(pageable),Student_Course.class,"student_course");
        Page<Student_Course> pageList = new PageImpl<Student_Course>(list, pageable, cnt);
        return pageList;
    }

    @Override
    public Page<Student_Course> findByCid(Integer cid,Integer pageNum) {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm = new PageModel();
        Query query = new Query();
        Criteria criteria = Criteria.where("cid").is(cid);
        query.addCriteria(criteria);

        // 设置当前页?
        pm.setPagenumber(pageNum);
        // 每页条数
        pm.setPagesize(10);
        pm.setSort(Sort.by(Sort.Direction.DESC, "sid"));
        pageable.setPage(pm);
        Long cnt = mongoTemplate.count(query, "student_course");
        List<Student_Course> list = mongoTemplate.find(query.with(pageable), Student_Course.class,"student_course");
        Page<Student_Course> pageList = new PageImpl<Student_Course>(list, pageable, cnt);
        return pageList;
    }

    @Override
    public Page<Student_Course> paginationQuery(Integer pageNum) {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm = new PageModel();
        Query query = new Query();
        // 设置当前页?
        pm.setPagenumber(pageNum);
        // 每页条数
        pm.setPagesize(10);
        pm.setSort(Sort.by(Sort.Direction.ASC, "_id"));
        pageable.setPage(pm);
        Long cnt = mongoTemplate.count(query, "student_course");
        List<Student_Course> list = mongoTemplate.find(query.with(pageable), Student_Course.class ,"student_course");

        Page<Student_Course> pageList = new PageImpl<Student_Course>(list, pageable, cnt);
        return pageList;
//        LookupOperation lookupToLots=LookupOperation.newLookup()
//                .from("student")
//                .localField("sid")
//                .foreignField("sid")
//                .as("groups");
//        //主表
//        Criteria ordercri = new Criteria();
//        AggregationOperation match = Aggregation.match(ordercri);
//        //次表
//        Criteria ordercri1 = new Criteria();
//        AggregationOperation match1 = Aggregation.match(ordercri1);
//        UnwindOperation unwinds = Aggregation.unwind("groups");
//        Aggregation aggregation = Aggregation.newAggregation(match,match1,lookupToLots,unwinds);
//        return mongoTemplate.aggregate(aggregation).getMappedResults();
    }

    @Override
    public List<StatisticStuCnt> statisticBySid(){
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("CID").count().as("stucount")
                ,Aggregation.sort(Sort.Direction.DESC,"stucount"));
        AggregationResults<StatisticStuCnt> output=mongoTemplate.aggregate(aggregation,"student_course",StatisticStuCnt.class);
        Iterator<StatisticStuCnt> iter;
        List<StatisticStuCnt> results = new ArrayList<>();
        int topn=10;
        for(iter = output.iterator();iter.hasNext();){
            if(topn>0) {
                StatisticStuCnt obj = iter.next();
                results.add(obj);
            }
            else break;
            topn--;
        }
        for(int i =0;i<results.size();i++){
            System.out.println(results.get(i).getCid() +" " +results.get(i).getStucnt());
            Course tmp = courseDao.findById(results.get(i).getCid());
            results.get(i).setName(tmp.getName());
        }
        return results;
    }

    @Override
    public List<StatisticStuCnt> statisticTopNAvg(){
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("CID").avg("SCORE").as("avgscore").count().as("stucount")
                ,Aggregation.sort(Sort.Direction.DESC,"avgscore"));
        AggregationResults<StatisticStuCnt> output=mongoTemplate.aggregate(aggregation,"student_course",StatisticStuCnt.class);
        Iterator<StatisticStuCnt> iter;
        List<StatisticStuCnt> results = new ArrayList<>();
        int topn=10;
        for(iter = output.iterator();iter.hasNext();){
            if(topn>0) {
                StatisticStuCnt obj = iter.next();
                results.add(obj);
            }
            else break;
            topn--;
        }
        for(int i =0;i<results.size();i++){
            System.out.println(results.get(i).getCid() +" " +results.get(i).getStucnt());
            Course tmp = courseDao.findById(results.get(i).getCid());
            results.get(i).setName(tmp.getName());
        }
        return results;
    }
}
