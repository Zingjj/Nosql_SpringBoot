package cn.itcast.springboot.dao;

import cn.itcast.springboot.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/11/30 13:14
 */
public interface StudentDao {
    boolean insert(Student student);
    Student findById(Long sid);
    List<Student> findByName(String name);
    Page<Student> paginationQuery(Integer pageNum);
    boolean update(Student student);
    boolean deleteById(Long sid);
    boolean deleteByName(String name);

}
