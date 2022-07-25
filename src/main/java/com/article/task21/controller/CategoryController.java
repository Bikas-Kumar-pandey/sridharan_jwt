package com.article.task21.controller;

import com.article.task21.dto.CategoryReq;
import com.article.task21.dto.CategoryRes;
import com.article.task21.entity.Category;
import com.article.task21.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/category")
    public CategoryRes createCategory(@RequestBody CategoryReq req){
        return service.createCategory(req);
    }

    @GetMapping("/category/{id}")
    public CategoryRes getById(@PathVariable int id){
        return service.findOneById(id);
    }

    @GetMapping("/category")
    public List<CategoryRes> getAllCategory(){
        return service.findAllCategory();
    }

    @PutMapping("/category/{id}")
    public CategoryRes updateCategory(@PathVariable int id,@RequestBody CategoryReq req){
        return service.updateCategory(id,req);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        return service.deleteCategory(id);
    }

}
