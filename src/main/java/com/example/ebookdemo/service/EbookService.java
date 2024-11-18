package com.example.ebookdemo.service;

import com.example.ebookdemo.config.EbookDemoApplication;
import com.example.ebookdemo.domain.Ebook;
import com.example.ebookdemo.domain.EbookExample;
import com.example.ebookdemo.mapper.EbookMapper;
import com.example.ebookdemo.req.EbookReq;
import com.example.ebookdemo.resp.EbookResp;
import com.example.ebookdemo.util.CopyUtil;
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

    public List<EbookResp> allLists (EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(1,5);
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);


        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);

        LOG.info("总条数:{}",pageInfo.getTotal());

        LOG.info("总页数:{}",pageInfo.getPages());


//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
//              对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//
//            respList.add(ebookResp);
//        }

        //列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);

        return list;
    }
}
