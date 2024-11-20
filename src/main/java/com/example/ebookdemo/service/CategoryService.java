package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.Category;
import com.example.ebookdemo.domain.CategoryExample;
import com.example.ebookdemo.mapper.CategoryMapper;
import com.example.ebookdemo.req.CategoryQueryReq;
import com.example.ebookdemo.req.CategorySaveReq;
import com.example.ebookdemo.resp.CategoryQueryResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.util.CopyUtil;
import com.example.ebookdemo.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> allLists (CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);


        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);

        LOG.info("总条数:{}",pageInfo.getTotal());

        LOG.info("总页数:{}",pageInfo.getPages());


        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        LOG.info("list: {}",list);
        LOG.info("数据: {}",pageResp);

        return pageResp;
    }

    public void saveCategory(CategorySaveReq categorySaveReq) {
        Category category = CopyUtil.copy(categorySaveReq, Category.class);
        if (ObjectUtils.isEmpty(categorySaveReq.getId())) {
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }else {
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete (Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
