package com.sardine.user;

import com.sardine.user.entity.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author keith
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SardineUserTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void insert(){
        Student student = new Student();
        student.setName("Jerry").setAge("22");
        mongoTemplate.insert(student);
    }

    @Test
    public void update(){
        Query query = Query.query(Criteria.where("name").is("Jerry"));
        mongoTemplate.updateFirst(query, Update.update("age", "18"), Student.class);
    }

    @Test
    public void find(){
        Query query = Query.query(Criteria.where("name").is("Jerry"));
        List<Student> students = mongoTemplate.find(query, Student.class);
        System.out.println(students);
    }

    @Test
    public void findPage(){
        Query query = Query.query(Criteria.where("name").is("Jerry"));
        query.with(PageRequest.of(0, 2)); // page从0开始
        query.with(Sort.by(Sort.Direction.ASC, "age"));
        List<Student> students = mongoTemplate.find(query, Student.class);
        System.out.println(students);
    }
}
