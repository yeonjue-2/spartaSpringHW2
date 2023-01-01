package com.example.springmemo.service;

import com.example.springmemo.dto.*;
import com.example.springmemo.entity.Comment;
import com.example.springmemo.entity.Post;
import com.example.springmemo.entity.User;
import com.example.springmemo.entity.UserRoleEnum;
import com.example.springmemo.jwt.JwtUtil;
import com.example.springmemo.repository.CommentRepository;
import com.example.springmemo.repository.PostRepository;
import com.example.springmemo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts() {
        List<PostResponse> list = new ArrayList<>();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();

        for (Post post : posts) {
            List<Comment> commentList = commentRepository.findAllByPostIdOrderByModifiedAtDesc(post.getId());
            if (commentList.size() != 0) {
                list.add(new PostResponse(post, post.getUser(), commentList));
            } else {
                list.add(new PostResponse(post, post.getUser()));
            }
        }

        return list;
    }

    @Transactional
    public PostResponse createPost(PostRequest requestDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Post post = new Post(requestDto, user);
        postRepository.save(post);
        return new PostResponse(post, user);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        List<Comment> commentList = commentRepository.findAllByPostIdOrderByModifiedAtDesc(post.getId());

        if (commentList.size() != 0) {
            return new PostResponse(post, post.getUser(), commentList);
        }

        return new PostResponse(post, post.getUser());
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest requestDto, String username) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다.")
        );

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        if (post.isWriter(user.getUsername())) {
            post.update(id, requestDto, user);
            postRepository.save(post);
        } else {
            throw new RuntimeException("해당 유저만 수정할 수 있습니다.");
        }

        return new PostResponse(post, user);
    }

    @Transactional
    public PostResponse adminUpdatePost(Long id, PostRequest requestDto, String username) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다.")
        );

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        post.update(id, requestDto, user);
        postRepository.save(post);

        return new PostResponse(post, user);
    }

    @Transactional
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다.")
        );

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        if (post.isWriter(user.getUsername())) {
            postRepository.deleteById(id);
        } else {
            throw new RuntimeException("해당 유저만 삭제할 수 있습니다.");
        }
    }

    @Transactional
    public void adminDeletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다.")
        );

        postRepository.deleteById(id);

    }
}
