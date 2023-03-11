package com.Ayano.demo0309.controller;


import com.Ayano.demo0309.entity.Student;
import com.Ayano.demo0309.mapper.StudentMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    RedisTemplate<String,Object> stringObjectRedisTemplate;

    /**
     *顯示所有學生
     * @param current 當前頁
     * @param size 每頁裡數據顯示的個數
     * @return
     */
    @RequestMapping("/select")
    public Page<Student> select(Integer current, Integer size){
        String redisKey = current + "student" + size;//給當前頁在redis裡起一個key
        ValueOperations<String, Object> opsForValue = stringObjectRedisTemplate.opsForValue();//set值
        SetOperations<String, Object> opsForSet = stringObjectRedisTemplate.opsForSet();

        //緩存功能實現
        Page<Student> pageResult = (Page<Student>)opsForValue.get(redisKey);
        if (pageResult == null){
            //1.如果緩存為空,就去mysql裡查找
        Page<Student> studentsPage = new Page<>(current,size);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
         pageResult = studentMapper.selectPage(studentsPage, queryWrapper);

        opsForValue.set(redisKey,pageResult);//kv放入緩存

            //记录下缓存本页数据时使用的key, 便于在删除缓存时知道应该删除哪些缓存
            opsForSet.add("cachedKey",redisKey);  //记录已经缓存过的key
            System.out.println("數據已緩存");
        }else{System.out.println("數據來自緩存");}
        //2.緩存非空
            return pageResult;
    }

    /**
     * 添加學生
     * @param students
     */
    @RequestMapping("add")
    public void add(Student students){
        studentMapper.insert(students);
        //對數據進行修改後需要修改緩存


        SetOperations<String, Object> opsForSet = stringObjectRedisTemplate.opsForSet();
        Set<Object> cacheKeys = opsForSet.members("cachedKey");
        List<String> stringList = cacheKeys.stream().map(obj -> {
            return (String) obj;
        }).collect(Collectors.toList());
        //2.删除所有缓存数据
        //3.用于记录缓存key的set也清空掉
        System.out.println(        stringObjectRedisTemplate.delete(stringList)
);
        System.out.println(        stringObjectRedisTemplate.delete("cachedKey")
);
//        cacheTool();
        System.out.println("緩存清理完畢");
    }

    /**
     * 修改學生
     * @param students
     */
    @RequestMapping("update")
    public void update(Student students){
        studentMapper.updateById(students);
//        cacheTool();
        //對數據進行修改後需要修改緩存
        //1.获取哪些页面做了缓存
        SetOperations<String, Object> opsForSet = stringObjectRedisTemplate.opsForSet();
        Set<Object> cacheKeys = opsForSet.members("cachedKey");
        List<String> stringList = cacheKeys.stream().map(obj -> {
            return (String) obj;
        }).collect(Collectors.toList());
        //2.删除所有缓存数据
        stringObjectRedisTemplate.delete(stringList);
        //3.用于记录缓存key的set也清空掉
        stringObjectRedisTemplate.delete("cachedKey");


        System.out.println("緩存清理完畢");


    }

    /**
     * 刪除學生
     * @param students
     */
    @RequestMapping("delete")
    public void delete(Student students){
        studentMapper.deleteById(students);
//        cacheTool();
        //對數據進行修改後需要修改緩存
        //1.获取哪些页面做了缓存

        SetOperations<String, Object> opsForSet = stringObjectRedisTemplate.opsForSet();
        Set<Object> cacheKeys = opsForSet.members("cachedKey");
        List<String> stringList = cacheKeys.stream().map(obj -> {
            return (String) obj;
        }).collect(Collectors.toList());
        //2.删除所有缓存数据
        stringObjectRedisTemplate.delete(stringList);
        //3.用于记录缓存key的set也清空掉
       stringObjectRedisTemplate.delete("cachedKey");
        System.out.println("緩存清理完畢");

    }

    private void cacheTool(){
        //對數據進行修改後需要修改緩存
        //1.获取哪些页面做了缓存
        SetOperations<String, Object> opsForSet = stringObjectRedisTemplate.opsForSet();
        Set<Object> cacheKeys = opsForSet.members("cachedKey");
        List<String> stringList = cacheKeys.stream().map(obj -> {
            return (String) obj;
        }).collect(Collectors.toList());
        //2.删除所有缓存数据
      stringObjectRedisTemplate.delete(stringList);
        //3.用于记录缓存key的set也清空掉
        stringObjectRedisTemplate.delete("cachedKey");

    }
}

