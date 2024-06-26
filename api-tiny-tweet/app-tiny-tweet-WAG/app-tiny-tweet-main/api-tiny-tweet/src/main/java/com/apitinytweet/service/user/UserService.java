package com.apitinytweet.service.user;

import com.apitinytweet.dto.user.UserRecord;
import com.apitinytweet.entity.user.User;
import com.apitinytweet.mapper.user.UserMapper;
import com.apitinytweet.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService
{

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public User saveUser(UserRecord userRecord){
		UserDetails userExists = userRepository.findByUsername(userRecord.username());
		if(Objects.nonNull(userExists))
			throw new InvalidParameterException("User already exists");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = userMapper.toUser(userRecord);
		user.setPassword(encoder.encode(userRecord.password()));

		return userRepository.save(user);
	}

	public User findUserById(Long id){
		return userRepository.findById(id).orElse(null);
	}

	public Page<User> listAllUsers(Pageable pageable){
		return userRepository.findAll(pageable);
	}

	public void deleteUser(Long id){
		userRepository.deleteById(id);
	}
}
