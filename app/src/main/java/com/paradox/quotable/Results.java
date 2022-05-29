package com.paradox.quotable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Results {

    String author;
    String content;
    String authorSlug;
    String tags;

    public Results(){

    }
    public Results(String author,String content,String authorSlug,String tags){
        this.author = author;
        this.content = content;
        this.authorSlug = authorSlug;
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthorSlug() {
        return authorSlug;
    }
    public void setAuthorSlug(String authorSlug) {
        this.authorSlug = authorSlug;
    }

}

