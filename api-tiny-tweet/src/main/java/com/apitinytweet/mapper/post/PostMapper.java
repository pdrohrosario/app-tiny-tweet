package com.apitinytweet.mapper.post;

import com.apitinytweet.dto.post.PostRecord;
import com.apitinytweet.entity.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper
{

	@Mapping(target = "userId", source = "user.id")
	PostRecord toPostRecord(Post post);

	@Mapping(source = "userId", target = "user.id")
	Post toPost(PostRecord postRecord);
}
