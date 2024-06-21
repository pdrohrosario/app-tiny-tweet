package com.apitinytweet.dto.post;

public record PostRecordResponse(
		Long id,
		String title,
		String content,
		Long userId,
		String userName
)
{
}
