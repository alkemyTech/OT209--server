package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.AuthenticateRequest;
import com.alkemy.ong.models.request.RegisterRequest;
import com.alkemy.ong.models.response.AuthenticateResponse;
import com.alkemy.ong.models.response.RegisterResponse;
import com.alkemy.ong.models.response.UserResponse;
import com.alkemy.ong.service.AuthService;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")

public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> signUp(@Valid @RequestBody RegisterRequest registerRequest) throws IOException {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticateResponse> signIn(@RequestBody AuthenticateRequest authRequest) throws Exception {
		return ResponseEntity.ok(authService.login(authRequest.getEmail(), authRequest.getPassword()));

	}

	@GetMapping("/me")
	public ResponseEntity<UserResponse> getUser(@RequestHeader(name = "Authorization") String token) {
		System.out.println(token);

		return ResponseEntity.status(HttpStatus.OK).body(authService.userAuth(token));
	}

}
