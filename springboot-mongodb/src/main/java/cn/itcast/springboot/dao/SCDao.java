package cn.itcast.springboot.dao;

import cn.itcast.springboot.pojo.StatisticStuCnt;
import cn.itcast.springboot.pojo.Student_Course;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 16:38
 */
public interface SCDao {
    boolean insert(Long sid,String cids);
    boolean update(Student_Course sc);
    // 通过学生学号和课程号来删除该选课记录
    boolean deleteBySidCid(Long sid,Integer cid);
    // 通过学生学号来查找其选课记录
    Page<Student_Course> findBySid(Long sid,Integer pageNum);
    Page<Student_Course> findByCid(Integer cid,Integer pageNum);
    // 查找全部选课记录
    Page<Student_Course> paginationQuery(Integer pageNum);
    List<StatisticStuCnt> statisticBySid();
    List<StatisticStuCnt> statisticTopNAvg();
}
