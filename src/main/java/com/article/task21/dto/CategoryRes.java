package com.article.task21.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class CategoryRes {
    private int id;
    private String categoryName;
    private List<ArticleRes> articleRes;
}
