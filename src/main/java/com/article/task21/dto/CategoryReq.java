package com.article.task21.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryReq {
    private String categoryName;
    private List<ArticleReq> articles;
}
