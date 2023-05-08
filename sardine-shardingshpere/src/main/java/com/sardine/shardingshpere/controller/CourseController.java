package com.sardine.shardingshpere.controller;

import com.sardine.shardingshpere.entity.Course;
import com.sardine.shardingshpere.mapper.CourseMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CourseController {

    @Resource
    private CourseMapper courseMapper;

    @GetMapping("insert")
    @Transactional
    public String insert() {
        Course course1 = new Course();
        course1.setCid(1L);
        course1.setCname("test");
        course1.setScore(1);
        courseMapper.insert(course1);

        Course course2 = new Course();
        course2.setCid(2L);
        course2.setCname("test");
        course2.setScore(1);
        courseMapper.insert(course2);

        Course course3 = new Course();
        course3.setCid(3L);
        course3.setCname("test");
        course3.setScore(2);
        courseMapper.insert(course3);

        Course course4 = new Course();
        course4.setCid(4L);
        course4.setCname("test");
        course4.setScore(2);
        courseMapper.insert(course4);

        return "success";
    }

    @GetMapping("insert2")
    public String insert2() {

        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("Java" + i);
            course.setScore(50 + 2 * i);
            courseMapper.insert(course);
        }

        return "success";
    }
}
