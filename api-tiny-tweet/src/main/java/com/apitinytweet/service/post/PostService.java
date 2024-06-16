package com.apitinytweet.service.post;

import com.apitinytweet.dto.post.PostRecord;
import com.apitinytweet.entity.post.Post;
import com.apitinytweet.mapper.post.PostMapper;
import com.apitinytweet.repository.post.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService
{

	private final PostRepository postRepository;
	private final PostMapper postMapper;

	public Post savePost(PostRecord postRecord){
		Post post = postMapper.toPost(postRecord);

		return postRepository.save(post);
	}

	public Post findPostById(Long id){
		return postRepository.findById(id).orElse(null);
	}

	public Page<Post> listAllPosts(Pageable pageable){
		return postRepository.findAll(pageable);
	}

	public void deletePost(Long id){
		postRepository.deleteById(id);
	}
}
