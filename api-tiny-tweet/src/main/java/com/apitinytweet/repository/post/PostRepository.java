package com.apitinytweet.repository.post;

import com.apitinytweet.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>
{

	Page<Post> findAllByUserId(Long userId, Pageable pageable);

	@Query("SELECT p FROM Post p JOIN FETCH p.user u")
	Page<Post> findAllPostsAndUsers(Pageable pageable);

}
