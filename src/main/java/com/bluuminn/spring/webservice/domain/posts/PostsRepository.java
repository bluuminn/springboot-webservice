package com.bluuminn.spring.webservice.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByDeletedAtIsNullOrderByIdDesc();

    Optional<Posts> findByIdAndDeletedAtIsNull(Long id);
}
