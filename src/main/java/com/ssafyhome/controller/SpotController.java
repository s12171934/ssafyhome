package com.ssafyhome.controller;

import com.ssafyhome.model.dto.SpotDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
		name = "Spot Controller",
		description = "카테고리 별 장소 핸들링"
)
@RestController
@RequestMapping("/spot")
public class SpotController {

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/")
	public ResponseEntity<List<SpotDto>> getSpots(
			@RequestParam
			String dongCode
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PostMapping("/")
	public ResponseEntity<?> updateSpotInfo() {

		return null;
	}
}
