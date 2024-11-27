package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.Document;
import com.example.ebookdemo.domain.DocumentExample;
import com.example.ebookdemo.mapper.DocumentMapper;
import com.example.ebookdemo.req.DocumentQueryReq;
import com.example.ebookdemo.req.DocumentSaveReq;
import com.example.ebookdemo.resp.DocumentQueryResp;
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
public class DocumentService {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);
    @Resource
    private DocumentMapper documentMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocumentQueryResp> allLists (DocumentQueryReq req) {
        DocumentExample documentExample = new DocumentExample();
        documentExample.setOrderByClause("sort asc");
        DocumentExample.Criteria criteria = documentExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Document> documentList = documentMapper.selectByExample(documentExample);


        PageInfo<Document> pageInfo = new PageInfo<>(documentList);

        LOG.info("总条数:{}",pageInfo.getTotal());

        LOG.info("总页数:{}",pageInfo.getPages());


        //列表复制
        List<DocumentQueryResp> list = CopyUtil.copyList(documentList, DocumentQueryResp.class);

        PageResp<DocumentQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        LOG.info("list: {}",list);
        LOG.info("数据: {}",pageResp);

        return pageResp;
    }

    public void saveDocument(DocumentSaveReq documentSaveReq) {
        Document document = CopyUtil.copy(documentSaveReq, Document.class);
        if (ObjectUtils.isEmpty(documentSaveReq.getId())) {
            //新增
            document.setId(snowFlake.nextId());
            documentMapper.insert(document);
        }else {
            //更新
            documentMapper.updateByPrimaryKey(document);
        }
    }

    public void delete (Long id) {
        documentMapper.deleteByPrimaryKey(id);
    }

    public void delete (List<String> ids) {
//        documentMapper.deleteByPrimaryKey(ids);
        DocumentExample documentExample = new DocumentExample();
        DocumentExample.Criteria criteria = documentExample.createCriteria();
        criteria.andIdIn(ids);
        documentMapper.deleteByExample(documentExample);
    }

    public List<DocumentQueryResp> allData () {
        DocumentExample documentExample = new DocumentExample();
        documentExample.setOrderByClause("sort asc");
        List<Document> documentList = documentMapper.selectByExample(documentExample);

        //列表复制
        List<DocumentQueryResp> list = CopyUtil.copyList(documentList, DocumentQueryResp.class);
        return list;
    }
}
