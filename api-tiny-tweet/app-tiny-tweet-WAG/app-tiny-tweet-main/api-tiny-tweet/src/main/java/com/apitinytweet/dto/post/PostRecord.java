package com.apitinytweet.dto.post;

public record PostRecord(
		Long id,
		String title,
		String content,
		Long userId
)
{
}

