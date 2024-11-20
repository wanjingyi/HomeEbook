package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.Ebook;
import com.example.ebookdemo.domain.EbookExample;
import com.example.ebookdemo.mapper.EbookMapper;
import com.example.ebookdemo.req.EbookQueryReq;
import com.example.ebookdemo.req.EbookSaveReq;
import com.example.ebookdemo.resp.EbookQueryResp;
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
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);
    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> allLists (EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);


        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);

        LOG.info("总条数:{}",pageInfo.getTotal());

        LOG.info("总页数:{}",pageInfo.getPages());


        //列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        LOG.info("list: {}",list);
        LOG.info("数据: {}",pageResp);

        return pageResp;
    }

    public void saveEbook(EbookSaveReq ebookSaveReq) {
        Ebook ebook = CopyUtil.copy(ebookSaveReq, Ebook.class);
        if (ObjectUtils.isEmpty(ebookSaveReq.getId())) {
            //新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        }else {
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete (Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
