package com.article.task21.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category implements Comparable<Category>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category_name",nullable = false,unique = true)
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category",fetch = FetchType.LAZY)
    private List<Article> articles=new ArrayList<>();

    @Override
    public int compareTo(Category category) {
        if(category.getId()<this.id) return 1;
        else return -1;

    }
}
