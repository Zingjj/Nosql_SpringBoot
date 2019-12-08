package cn.itcast.springboot.dao;

import cn.itcast.springboot.pojo.Course;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 15:59
 */
public interface CourseDao {
    boolean insert(Course course);
    boolean update(Course course);
    boolean deleteById(Integer id);
    Course findById(Integer id);
    List<Course> findByName(String name);
    Page<Course> paginationQuery(Integer pageNum);
    List<Course> search();
}
