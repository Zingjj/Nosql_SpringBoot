package cn.itcast.springboot.Controller;

import cn.itcast.springboot.dao.CourseDao;
import cn.itcast.springboot.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 16:08
 */
@RestController
public class CourseController {

    @Autowired
    private CourseDao courseDao;

    @PostMapping("/course/insert")
    public boolean insert(Course course) {
        return courseDao.insert(course);
    }

    @GetMapping("/course/deleteById")
    public boolean deleteById(Integer cid){
        return courseDao.deleteById(cid);
    }

    @PostMapping("/course/update")
    public boolean update(Course course){
        return courseDao.update(course);
    }

    @GetMapping("/course/search")
    public Page<Course> findAll(@RequestParam Integer pageNum) {return courseDao.paginationQuery(pageNum);}

    @GetMapping("/course/searchByid")
    public Course searchByid(Integer id) {return courseDao.findById(id);}

    @GetMapping("/course/searchByName")
    public List<Course> searchByName(String name) {return courseDao.findByName(name);}

    @GetMapping("/course/searchall")
    public List<Course> search(){return courseDao.search();}
}
