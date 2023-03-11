package com.Ayano.demo0309.service.impl;

import com.Ayano.demo0309.entity.Student;
import com.Ayano.demo0309.mapper.StudentMapper;
import com.Ayano.demo0309.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ayano
 * @since 2023-03-09 09:52:32
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
