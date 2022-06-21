package com.simba.book.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void cleanUp(){
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "simba0502@naver.com";
        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        List<Post> posts = postRepository.findAll();

        //then
        Post post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
    }

    @Test
    public void BaseTimeEntiy_등록() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.of(2022,06,20,0,0,0);
        postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Post> posts = postRepository.findAll();

        //then
        Post post = posts.get(0);

        System.out.println(">>>>>>>> createDate = " + post.getCreatedDate()+", modifiedDate = " + post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }

}