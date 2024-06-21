package com.apitinytweet.controller;

import com.apitinytweet.dto.LoginReturnRecord;
import com.apitinytweet.dto.user.LoginRecord;
import com.apitinytweet.entity.user.User;
import com.apitinytweet.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController
{
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<LoginReturnRecord> login(@RequestBody LoginRecord loginRecord) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(loginRecord.username(), loginRecord.password());
		var authentication = authenticationManager.authenticate(usernamePassword);
		User user = (User) authentication.getPrincipal();

		var token = tokenService.generateToken(user);

		return ResponseEntity.ok(new LoginReturnRecord(user.getUsername(), user.getId(), token));
	}
}
