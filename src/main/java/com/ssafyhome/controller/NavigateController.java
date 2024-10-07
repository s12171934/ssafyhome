package com.ssafyhome.controller;

import com.ssafyhome.handler.accessdeniedhandler.AccessDeniedHandler;
import com.ssafyhome.model.dto.NavigateDto;
import com.ssafyhome.model.dto.SpotSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
		name = "Navigate Controller",
		description = "부동산 매물과 각 장소간의 거리를 외부 API와 통신"
)
@RestController
@RequestMapping("/navigate")
public class NavigateController {

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/search")
	public ResponseEntity<NavigateDto> getTimeWithSearchSpot(
			@RequestParam
			String houseSeq,

			@RequestBody
			SpotSearchDto spotSearchDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/bookmark")
	@PreAuthorize("isAuthenticated()")
	@AccessDeniedHandler
	public ResponseEntity<List<NavigateDto>> getTimeWithCustomSpots(
			@RequestParam
			String houseSeq
	) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/spot")
	public ResponseEntity<List<NavigateDto>> getTimeWithSpots(
			@RequestParam
			String houseSeq
	) {

		return null;
	}
}
