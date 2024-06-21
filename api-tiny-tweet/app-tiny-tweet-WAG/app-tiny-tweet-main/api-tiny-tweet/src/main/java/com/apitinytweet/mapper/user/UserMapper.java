package com.apitinytweet.mapper.user;

import com.apitinytweet.dto.user.UserRecord;
import com.apitinytweet.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper
{
	UserRecord toUserRecord(User user);

	User toUser(UserRecord userRecord);
}
