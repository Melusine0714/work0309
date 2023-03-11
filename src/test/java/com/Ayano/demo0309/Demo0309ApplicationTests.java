package com.Ayano.demo0309;

import com.Ayano.demo0309.mapper.StudentMapper;
import com.Ayano.demo0309.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo0309ApplicationTests {
    @Autowired
    private StudentService studentService;

    @Test
    void contextLoads() {
    }

    @Test
    void selectAll(){
        System.out.println(studentService.selectList());
    }

}
