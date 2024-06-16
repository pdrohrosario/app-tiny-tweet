package com.apitinytweet.controller.user;

import com.apitinytweet.dto.user.UserRecord;
import com.apitinytweet.mapper.user.UserMapper;
import com.apitinytweet.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
@RequestMapping("/user")
public class UserController
{

	private final UserService userService;
	private final UserMapper userMapper;

	@PostMapping("/save")
	public ResponseEntity<UserRecord> saveUser(@RequestBody UserRecord userRecord){
		try {
			var userResponse =userMapper.toUserRecord(userService.saveUser(userRecord));
			return ResponseEntity.ok(userResponse);
		} catch (BadRequestException e) {
			return ResponseEntity.badRequest().build();
        }
	}

	@GetMapping("/list")
	public ResponseEntity<Page<UserRecord>> listAllUsers(Pageable pageable){
		Page<UserRecord> userRecords = userService.listAllUsers(pageable).map(userMapper::toUserRecord);

		return ResponseEntity.ok(userRecords);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserRecord> findUserById(@PathVariable Long id){
		return ResponseEntity.ok(userMapper.toUserRecord(userService.findUserById(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);

		return ResponseEntity.ok().build();
	}
}
