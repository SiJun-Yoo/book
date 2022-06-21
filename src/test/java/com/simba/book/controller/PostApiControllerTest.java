package com.simba.book.controller;

import com.simba.book.domain.post.Post;
import com.simba.book.domain.post.PostRepository;
import com.simba.book.dto.PostUpdateRequestDto;
import com.simba.book.dto.PostSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void cleanUp(){
        postRepository.deleteAll();
    }

    @Test
    public void Post_등록() throws Exception{
        //given
        String title = "title";
        String content = "content";
        String author = "author";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:"+port+"/api/v1/post";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> posts = postRepository.findAll();
        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
        assertThat(posts.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    public void Post_수정() throws Exception{
        //given
        String title = "title";
        String content = "content";
        String author = "author";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Long updateId = post.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        String url = "http://localhost:" + port + "/api/v1/post/" + updateId;
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> posts = postRepository.findAll();
        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
    }
}
