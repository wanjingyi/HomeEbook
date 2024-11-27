package com.example.ebookdemo.mapper;

import com.example.ebookdemo.domain.Document;
import com.example.ebookdemo.domain.DocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DocumentMapper {
    long countByExample(DocumentExample example);

    int deleteByExample(DocumentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Document record);

    int insertSelective(Document record);

    List<Document> selectByExample(DocumentExample example);

    Document selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Document record, @Param("example") DocumentExample example);

    int updateByExample(@Param("record") Document record, @Param("example") DocumentExample example);

    int updateByPrimaryKeySelective(Document record);

    int updateByPrimaryKey(Document record);
}