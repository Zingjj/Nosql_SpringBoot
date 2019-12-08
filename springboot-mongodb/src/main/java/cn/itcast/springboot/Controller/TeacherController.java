package cn.itcast.springboot.Controller;

import cn.itcast.springboot.dao.TeacherDao;
import cn.itcast.springboot.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 14:51
 */
@RestController
public class TeacherController {
    @Autowired
    private TeacherDao teacherDao;

    @PostMapping("/teacher/insert")
    public boolean insert(Teacher teacher){
        return teacherDao.insert(teacher);
    }

    @GetMapping("/teacher/deleteById")
    public boolean deleteById(Integer tid){
        return teacherDao.deleteById(tid);
    }

    @PostMapping("/teacher/update")
    public boolean update(Teacher teacher){
        return teacherDao.update(teacher);
    }

    @GetMapping("/teacher/search")
    public Page<Teacher> search(Integer pageNum){
        return teacherDao.paginationQuery(pageNum);
    }

    @GetMapping("/teacher/searchById")
    public Teacher searchById(Integer id){
        return teacherDao.findById(id);
    }

    @GetMapping("/teacher/searchByName")
    public List<Teacher> searchByName(String name){
        return teacherDao.findByName(name);
    }


}
