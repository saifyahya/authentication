package com.authentication.lab14.models;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private SiteUser user;

    public Post(String content, SiteUser user) {
        this.content = content;
        this.user = user;
    }

    public Post() {
    }

    public String getContent() {
        return content;
    }

    public SiteUser getUser() {
        return user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }
}


