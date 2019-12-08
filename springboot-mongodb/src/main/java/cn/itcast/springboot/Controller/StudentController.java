package cn.itcast.springboot.Controller;

import cn.itcast.springboot.dao.StudentDao;
import cn.itcast.springboot.pojo.Student;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author: Zing
 * @date: 2019/11/30 14:10
 */
@RestController
public class StudentController {
    @Autowired
    private StudentDao studentDao;

    @PostMapping("/student/insert")
    public boolean insert(Student student) {
        System.out.println(student.getBirthday());
        return studentDao.insert(student);
    }

    @GetMapping("/student/deleteById")
    public boolean deleteById(Long sid){
        return studentDao.deleteById(sid);
    }

    @GetMapping("/student/deleteByName")
    public boolean deleteByName(String name){
        return studentDao.deleteByName(name);
    }

    @PostMapping("/student/update")
    public boolean update( Student student){
        return studentDao.update(student);
    }

    @GetMapping("/student/search")
    public Page<Student> findAll(@RequestParam Integer pageNum) {return studentDao.paginationQuery(pageNum);}

    @GetMapping("/student/searchBySid")
    public Student searchBySid(Long sid) {return studentDao.findById(sid);}

    @GetMapping("/student/searchByName")
    public List<Student> searchByName(String name) {return studentDao.findByName(name);}

}
