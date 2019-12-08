package cn.itcast.springboot.Controller;

import cn.itcast.springboot.dao.SCDao;
import cn.itcast.springboot.pojo.StatisticStuCnt;
import cn.itcast.springboot.pojo.Student_Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Zing
 * @date: 2019/12/1 18:39
 */
@RestController
public class SCController {

    @Autowired
    private SCDao scDao;

    @PostMapping("/sc/insert")
    private boolean insert(Long sid,String cids){
        return scDao.insert(sid,cids);
    }

    @PostMapping("/sc/update")
    private boolean update(Student_Course sc){
        return scDao.update(sc);
    }

    @GetMapping("/sc/deleteBySidCid")
    private boolean deleteBySidCid(@RequestParam(value = "sid") Long sid,
                                   @RequestParam(value = "cid") Integer cid){
        return scDao.deleteBySidCid(sid,cid);
    }

    @GetMapping("/sc/findBySid")
    private Page<Student_Course> findBySid(@RequestParam(value = "sid") Long sid,
                                           @RequestParam(value = "pageNum") Integer pageNum){
        return scDao.findBySid(sid,pageNum);
    }

    @GetMapping("/sc/findByCid")
    private Page<Student_Course> findByCid(@RequestParam(value = "cid") Integer cid,
                                           @RequestParam(value = "pageNum") Integer pageNum){
        return scDao.findByCid(cid,pageNum);
    }

    @GetMapping("/sc/search")
    private Page<Student_Course> search(@RequestParam Integer pageNum){
        return scDao.paginationQuery(pageNum);
    }


    @GetMapping("/sc/statisticBySid")
    private List<StatisticStuCnt> statisticBySid(){return scDao.statisticBySid();}

    @GetMapping("/sc/statisticTopNAvg")
    private List<StatisticStuCnt> statisticTopNAvg(){return scDao.statisticTopNAvg();}

}
