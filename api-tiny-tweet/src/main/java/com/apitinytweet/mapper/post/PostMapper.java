package com.apitinytweet.mapper.post;

import com.apitinytweet.dto.post.PostRecord;
import com.apitinytweet.entity.post.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper
{

	PostRecord toPostRecord(Post post);

	Post toPost(PostRecord postRecord);
}
