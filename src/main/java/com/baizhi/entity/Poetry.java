package com.baizhi.entity;

import com.baizhi.entity.Poet;

import java.io.Serializable;

public class Poetry implements Serializable {
	
    private Integer id;

    private String title;

    private String content;

    private Poet poet;

    private String author;

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Poetry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", poet=" + poet +
                ", author='" + author + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Poet getPoet() {
        return poet;
    }

    public void setPoet(Poet poet) {
        this.poet = poet;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Poetry(Integer id, String title, String content, Poet poet, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.poet = poet;
        this.author = author;
    }

    public Poetry() {
    }
}
