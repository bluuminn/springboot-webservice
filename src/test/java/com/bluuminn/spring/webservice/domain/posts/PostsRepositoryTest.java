package com.bluuminn.spring.webservice.domain.posts;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void cleanUp() {
        log.info("--- clean up ---");
        postsRepository.deleteAll();
    }

    @DisplayName("게시글 저장 후 불러오기")
    @Test
    void save_and_find() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("bluuminn@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @DisplayName("BaseTimeEntity 등록")
    @Test
    void resist_BaseTimeEntity() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 4, 3, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        log.info(">>>>> createdAt=" + posts.getCreatedAt() + ", updatedAt=" + posts.getUpdatedAt());

        assertThat(posts.getCreatedAt()).isAfter(now);
        assertThat(posts.getUpdatedAt()).isAfter(now);
    }
}