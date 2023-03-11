package com.Ayano.demo0309.mapper;

import com.Ayano.demo0309.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ayano
 * @since 2023-03-09 09:52:32
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
