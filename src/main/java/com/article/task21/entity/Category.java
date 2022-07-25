package com.article.task21.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Category implements Comparable<Category>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category_name")
    private String categoryName;

    @Override
    public int compareTo(Category category) {
        if(category.getId()<this.id) return 1;
        else return -1;

    }
}
