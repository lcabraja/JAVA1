/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author doss
 */
public class Article {
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private int id;
    private String title;
    private String link;
    private String description;
    private String picturePath;
    private LocalDateTime publishedDate;

    public Article() {
    }
    
    public Article(String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.picturePath = picturePath;
        this.publishedDate = publishedDate;
    }
    
    public Article(int id, String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this(title, link, description, picturePath, publishedDate);
        this.id = id;
    }    

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }
}
