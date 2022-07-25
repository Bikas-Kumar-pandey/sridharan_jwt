package com.article.task21.service;

import com.article.task21.dto.ArticleReq;
import com.article.task21.dto.ArticleRes;
import com.article.task21.dto.CategoryReq;
import com.article.task21.dto.CategoryRes;
import com.article.task21.entity.Article;
import com.article.task21.entity.Category;
import com.article.task21.exception.Response;
import com.article.task21.repo.ArticleRepo;
import com.article.task21.repo.CategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final ArticleRepo articleRepo;

    public CategoryService(CategoryRepo categoryRepo, ArticleRepo articleRepo) {
        this.categoryRepo = categoryRepo;
        this.articleRepo = articleRepo;
    }

    public CategoryRes createCategory(CategoryReq req) {
        Category category;
        String categoryName = req.getCategoryName();
        if(categoryRepo.existsByCategoryName(categoryName)){
            category = categoryRepo.findByCategoryName(categoryName).get();
            List<Article> articles = category.getArticles();
            for(ArticleReq articleReq:req.getArticles()){
                Article article=new Article();
                article.setTitle(articleReq.getTitle());
                article.setLocation(articleReq.getLocation());
                article.setTime(articleReq.getTime());
                article.setLocation(articleReq.getLocation());
                article.setDescription(articleReq.getDescription());
                article.setCategory(category);
                articles.add(article);
            }
            category.setArticles(articles);
        }
        else {
            category=new Category();
            category.setCategoryName(req.getCategoryName());
            List<Article> articles=new ArrayList<>();
            for(ArticleReq articleReq:req.getArticles()){
                Article article=new Article();
                article.setTitle(articleReq.getTitle());
                article.setLocation(articleReq.getLocation());
                article.setTime(articleReq.getTime());
                article.setLocation(articleReq.getLocation());
                article.setDescription(articleReq.getDescription());
                article.setCategory(category);
                articles.add(article);
            }
            category.setArticles(articles);
        }
        return entityToDto(categoryRepo.save(category));
    }

    public CategoryRes findOneById(int id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        return entityToDto(category.get());
    }

    public List<CategoryRes> findAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        Collections.sort(categories);
        List<CategoryRes> categoryRes=new ArrayList<>();
        for(Category category:categories){
            categoryRes.add(entityToDto(category));
        }
        return categoryRes;
    }

    public CategoryRes updateCategory(int id,CategoryReq req) {
        Optional<Category> categorySaved = categoryRepo.findById(id);
        if (categorySaved.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        Category category=categorySaved.get();
        if(!category.getCategoryName().equals(req.getCategoryName())){
            throw new RuntimeException("Category name should match");
        }

        List<ArticleReq> articlesReq = req.getArticles();
        List<Article> articles=new ArrayList<>();

        List<Article> articles1 = category.getArticles();

        List<Integer> articleIdList = articles1.stream().map(Article::getId).collect(Collectors.toList());
        log.info("Article ID to be deleted : "+articleIdList);

        category.getArticles().clear();

        for(ArticleReq articleReq:articlesReq){
            Article article=new Article();
            article.setTitle(articleReq.getTitle());
            article.setLocation(articleReq.getLocation());
            article.setTime(articleReq.getTime());
            article.setDescription(articleReq.getDescription());
            article.setCategory(category);
            articles.add(article);
        }
        category.setArticles(articles);

        CategoryRes categoryRes = entityToDto(categoryRepo.save(category));

        articleRepo.deleteAllById(articleIdList);

        return categoryRes;
    }

    public ResponseEntity<Response> deleteCategory(int id){
        Optional<Category> categorySaved = categoryRepo.findById(id);
        if (categorySaved.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        categoryRepo.delete(categorySaved.get());
        Response response=new Response();
        response.setStatus("ok");
        response.setMessage("Category deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private CategoryRes entityToDto(Category category){

        CategoryRes categoryRes=new CategoryRes();

        categoryRes.setCategoryName(category.getCategoryName());
        categoryRes.setId(category.getId());

        List<Article> articles = category.getArticles();

        List<ArticleRes> articleResList=new ArrayList<>();
        for(Article article:articles){
            ArticleRes articleRes=new ArticleRes();
            articleRes.setId(article.getId());
            articleRes.setTitle(article.getTitle());
            articleRes.setTime(article.getTime());
            articleRes.setLocation(article.getLocation());
            articleRes.setDescription(article.getDescription());
            articleResList.add(articleRes);
        }

        categoryRes.setArticleRes(articleResList);

        return categoryRes;

    }

}
