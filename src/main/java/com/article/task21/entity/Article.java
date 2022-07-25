package com.article.task21.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Timestamp time;
    private String location;
    private String description;
    private int categoryId;

}
