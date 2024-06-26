package com.apitinytweet.controller.post;

import com.apitinytweet.dto.post.PostRecord;
import com.apitinytweet.dto.post.PostRecordResponse;
import com.apitinytweet.mapper.post.PostMapper;
import com.apitinytweet.mapper.post.PostResponseMapper;
import com.apitinytweet.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController
{

	private final PostService postService;
	private final PostMapper postMapper;
	private final PostResponseMapper postResponseMapper;

	@PostMapping("/save")
	public ResponseEntity<PostRecord> savePost(@RequestBody PostRecord postRecord){
		return ResponseEntity.ok(postMapper.toPostRecord(postService.savePost(postRecord)));
	}

	@GetMapping("/list")
	public ResponseEntity<Page<PostRecordResponse>> listAllPosts(Pageable pageable){
		Page<PostRecordResponse> postRecords = postService.listAllPostsAndUsers(pageable).map(postResponseMapper::toPostRecordResponse);

		return ResponseEntity.ok(postRecords);
	}

	@GetMapping("/list/{userId}")
	public ResponseEntity<Page<PostRecord>> listAllPostsByUserId(@PathVariable Long userId, Pageable pageable){
		Page<PostRecord> postRecords = postService.listAllPostsByUserId(userId, pageable).map(postMapper::toPostRecord);

		return ResponseEntity.ok(postRecords);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostRecord> findPostById(@PathVariable Long id){
		return ResponseEntity.ok(postMapper.toPostRecord(postService.findPostById(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id){
		postService.deletePost(id);

		return ResponseEntity.ok().build();
	}
}
