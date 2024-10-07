package com.ssafyhome.controller;

import com.ssafyhome.model.dto.FindUserDto;
import com.ssafyhome.model.dto.PasswordDto;
import com.ssafyhome.model.dto.UserDto;
import com.ssafyhome.model.dto.UserSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
		name = "User Controller",
		description = "회원가입 및 로그인 등 회원관리"
)
@RestController
@RequestMapping("/user")
public class UserController {

	@Operation(
			summary = "",
			description = ""
	)
	@PostMapping("/")
	public ResponseEntity<?> registerUser(
			@RequestBody
			UserDto userDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PostMapping("/find/{type}")
	public ResponseEntity<?> findUserInfo(
			@PathVariable
			String type,

			@RequestBody
			FindUserDto findUserDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/")
	public ResponseEntity<UserDto> getUserInfo(
			@RequestParam
			String userSeq
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/list")
	public ResponseEntity<List<UserDto>> getUserList(
			@RequestBody
			UserSearchDto userSearchDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/check/mail")
	public ResponseEntity<?> checkEmailSecret(
			@RequestParam
			String key
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/check/duplicate")
	public ResponseEntity<?> checkIdDuplicate(
			@RequestParam
			String userId
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PatchMapping("/{userSeq}")
	public ResponseEntity<?> changePassword(
			@PathVariable
			String userSeq,

			@RequestBody
			PasswordDto passwordDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PutMapping("/{userSeq}")
	public ResponseEntity<?> updateUserInfo(
			@PathVariable
			String userSeq,

			@RequestBody
			UserDto userDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@DeleteMapping("/{userSeq}")
	public ResponseEntity<?> deleteUser(
			@PathVariable
			String userSeq
	) {

		return null;
	}
}
