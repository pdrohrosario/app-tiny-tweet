package com.apitinytweet.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.apitinytweet.entity.user.User;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

@Service
public class TokenService
{

	public String generateToken(User user){
		try{
			Algorithm algorithm = Algorithm.HMAC256("12345");
			return JWT.create()
				.withIssuer("desempregoERP")
				.withSubject(user.getUsername())
				.withExpiresAt(getExpirationDate())
				.sign(algorithm);
		}catch(JWTCreationException e){
			throw new RuntimeException("Erro criando token JWT - " + e.getMessage());
		}
	}

	public String validateToken(String token) {
		try{
			Algorithm algorithm = Algorithm.HMAC256("12345");
			return JWT.require(algorithm)
				.withIssuer("desempregoERP")
				.build()
				.verify(token)
				.getSubject();
		}catch (JWTVerificationException e){
			return "";
		}
	}

	private Instant getExpirationDate(){
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
