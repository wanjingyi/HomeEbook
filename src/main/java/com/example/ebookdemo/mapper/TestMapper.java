package com.example.ebookdemo.mapper;


import com.example.ebookdemo.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface TestMapper {
    public List<Test> list();
}
