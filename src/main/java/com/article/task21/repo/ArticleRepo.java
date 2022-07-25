package com.article.task21.repo;

import com.article.task21.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article ,Integer> {
    void deleteByCategory(int id);
}
