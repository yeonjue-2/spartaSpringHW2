package com.example.springmemo.service;

import com.example.springmemo.dto.PostDeleteRequestDto;
import com.example.springmemo.dto.PostRequestDto;
import com.example.springmemo.dto.PostResponseDto;
import com.example.springmemo.entity.Post;
import com.example.springmemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> result = posts.stream()
                .map(PostResponseDto::new)       //. map(o -> new PostResponseDto(o))
                .collect(Collectors.toList());
        return result;

    }

    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;

    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!post.isValidPasswaord(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        post.update(id, requestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public String deletePost(Long id, PostDeleteRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!post.isValidPasswaord(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        postRepository.deleteById(id);
        return "삭제 성공";
    }
}
