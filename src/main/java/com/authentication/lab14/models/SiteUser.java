package com.authentication.lab14.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
private String userName;
private String password;
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Post> posts;

    public SiteUser(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email=email;
    }
public SiteUser(){

}

    public Long getId() {
        return id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
