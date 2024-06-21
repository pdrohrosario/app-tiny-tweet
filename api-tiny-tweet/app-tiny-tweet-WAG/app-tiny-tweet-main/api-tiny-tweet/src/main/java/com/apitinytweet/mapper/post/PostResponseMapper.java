package com.apitinytweet.mapper.post;

import com.apitinytweet.dto.post.PostRecord;
import com.apitinytweet.dto.post.PostRecordResponse;
import com.apitinytweet.entity.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostResponseMapper
{

	@Mappings({
			@Mapping(target = "userId", source = "user.id"),
			@Mapping(target = "userName", source = "user.username")
	})
	PostRecordResponse toPostRecordResponse(Post post);

	@Mappings({
			@Mapping(source = "userId", target = "user.id"),
			@Mapping(source = "userName", target = "user.username")
	})
	Post toPost(PostRecordResponse postRecord);
}
