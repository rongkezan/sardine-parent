package com.sardine.cookbook.app.controller;

import com.sardine.cookbook.app.entity.Cookbook;
import com.sardine.cookbook.app.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("provider")
public class ProviderController {

    @GetMapping("hello")
    public String hello(String name){
        return "Hello " + name;
    }

    @GetMapping("cookbook")
    public Cookbook cookbook(String name){
        return Cookbook.builder().id(1L).bookName(name).build();
    }

    @GetMapping("list/cookbook")
    public List<Cookbook> listCookbook(){
        List<Cookbook> list = new ArrayList<>();
        list.add(Cookbook.builder().id(1L).bookName("盐酥鸡").build());
        list.add(Cookbook.builder().id(2L).bookName("酱鸭").build());
        return list;
    }

    @PostMapping("cookbook")
    public Cookbook postCookbook(@RequestBody Student student){
        return Cookbook.builder().bookName(student.getName()).build();
    }

    @GetMapping("balance")
    public String balance(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello World";
    }
}
