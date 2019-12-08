package cn.itcast.springboot.dao;

import cn.itcast.springboot.pojo.Teacher;
import cn.itcast.springboot.pojo.Teacher;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 12:48
 */
public interface TeacherDao {
    boolean insert(Teacher teacher);
    boolean update(Teacher teacher);
    boolean deleteById(Integer tid);
//    boolean deleteByName(String name);
    Teacher findById(Integer tid);
    List<Teacher> findByName(String name);
    Page<Teacher> paginationQuery(Integer pageNum);
//    Page<Student> searchCourseInpages()
}
