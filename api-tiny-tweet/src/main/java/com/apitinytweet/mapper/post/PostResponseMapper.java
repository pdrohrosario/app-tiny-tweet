package com.apitinytweet.mapper.post;

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
			@Mapping(target = "username", source = "user.username")
	})
	PostRecordResponse toPostRecordResponse(Post post);

	@Mappings({
			@Mapping(source = "userId", target = "user.id"),
			@Mapping(source = "username", target = "user.username")
	})
	Post toPost(PostRecordResponse postRecord);
}
