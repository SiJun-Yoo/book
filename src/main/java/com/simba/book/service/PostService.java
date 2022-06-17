package com.simba.book.service;

import com.simba.book.domain.post.Post;
import com.simba.book.domain.post.PostRepository;
import com.simba.book.dto.PostUpdateRequestDto;
import com.simba.book.dto.user.PostResponseDto;
import com.simba.book.dto.user.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long save(PostSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        post.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostResponseDto findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostResponseDto(post);
    }
}
