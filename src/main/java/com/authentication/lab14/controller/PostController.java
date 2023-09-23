package com.authentication.lab14.controller;

import com.authentication.lab14.models.Post;
import com.authentication.lab14.models.SiteUser;
import com.authentication.lab14.repositories.PostRepo;
import com.authentication.lab14.repositories.SiteUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {
PostRepo postRepo;
SiteUserRepo userRepo;
@Autowired
    public PostController(PostRepo postRepo, SiteUserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }



    @PostMapping("/add-post")
    public RedirectView addPost(String content, Long userId) {

    SiteUser userFromDB =userRepo.findById(userId).get();
        System.out.println("user want to post"+userId);
    if(userFromDB==null){
        System.out.println("user = null");
        return new RedirectView("/secretHome");}
        Post newPost = new Post(content,userFromDB);
        postRepo.save(newPost);
        return new RedirectView("/secretHome/"+userId);
    }

}
