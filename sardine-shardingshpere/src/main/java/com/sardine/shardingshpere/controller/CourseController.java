package com.sardine.shardingshpere.controller;

import com.sardine.shardingshpere.entity.Course;
import com.sardine.shardingshpere.mapper.CourseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CourseController {

    @Resource
    private CourseMapper courseMapper;

    @GetMapping("insert")
    public String insert(){

        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("Java" + i);
            courseMapper.insert(course);
        }

        return "success";
    }
}
