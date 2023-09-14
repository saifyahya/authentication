package com.authentication.lab14.repositories;

import com.authentication.lab14.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Long> {
}
