package com.ssafyhome.controller;

import com.ssafyhome.model.dto.FindUserDto;
import com.ssafyhome.model.dto.PasswordDto;
import com.ssafyhome.model.dto.UserDto;
import com.ssafyhome.model.dto.UserSearchDto;
import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import com.ssafyhome.model.dto.entity.redis.EmailSecretEntity;
import com.ssafyhome.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
		name = "User Controller",
		description = "회원가입 및 로그인 등 회원관리"
)
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {

		this.userService = userService;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PostMapping("/")
	public ResponseEntity<?> registerUser(
			@RequestBody
			UserDto userDto
	) {

		return userService.register(userDto);
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

		return type.equals("id") ?
				userService.findUserId(findUserDto) :
				userService.findPassword(findUserDto);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PostMapping("/send/mail")
	public ResponseEntity<?> sendEmail(
			@RequestBody
			String email
	) {

		return userService.sendEmail(email);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN') or  #userSeq == authentication.name")
	public ResponseEntity<UserDto> getUserInfo(
			@RequestParam
			String userSeq
	) {

		return userService.getUserInfo(userSeq);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserDto>> getUserList(
			@RequestBody
			UserSearchDto userSearchDto
	) {

		return userService.getUserList(userSearchDto);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/check/mail")
	public ResponseEntity<?> checkEmailSecret(
			@RequestParam
			String email,

			@RequestParam
			String key
	) {

		return userService.checkEmailSecret(new EmailSecretEntity(email, key));
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

		return userService.checkIdDuplicate(userId);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PatchMapping("/{userSeq}")
	public ResponseEntity<?> changePassword(
			@PathVariable(required = false)
			String userSeq,

			@RequestBody
			PasswordDto passwordDto
	) {

		return userService.changePassword(userSeq, passwordDto);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PutMapping("/{userSeq}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or  #userSeq == authentication.name")
	public ResponseEntity<?> updateUserInfo(
			@PathVariable
			String userSeq,

			@RequestBody
			UserEntity userEntity
	) {

		return userService.updateUser(userEntity);
	}

	@Operation(
			summary = "",
			description = ""
	)
	@DeleteMapping("/{userSeq}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or  #userSeq == authentication.name")
	public ResponseEntity<?> deleteUser(
			@PathVariable
			String userSeq
	) {

		return userService.deleteUser(userSeq);
	}
}
