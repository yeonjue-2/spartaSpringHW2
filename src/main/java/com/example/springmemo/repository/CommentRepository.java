package com.example.springmemo.repository;

import com.example.springmemo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByModifiedAtDesc(Long postId);

}
