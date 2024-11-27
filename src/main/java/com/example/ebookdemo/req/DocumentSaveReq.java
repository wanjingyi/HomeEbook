package com.example.ebookdemo.req;

import jakarta.validation.constraints.NotNull;

public class DocumentSaveReq {
    private Long id;

    @NotNull(message = "[电子书ID不能为空]")
    private Long ebookId;

    @NotNull(message = "[电子书父ID不能为空]")
    private Long parent;

    @NotNull(message = "[电子书书名不能为空]")
    private String name;

    @NotNull(message = "[排序不能为空]")
    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;

    private Integer commentCount;

    @NotNull(message = "[文本内容不能为空]")
    private String content;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DocumentSaveReq{" +
                "id=" + id +
                ", ebookId=" + ebookId +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", viewCount=" + viewCount +
                ", voteCount=" + voteCount +
                ", commentCount=" + commentCount +
                ", content='" + content + '\'' +
                '}';
    }
}