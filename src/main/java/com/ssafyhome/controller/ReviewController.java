package com.ssafyhome.controller;

import com.ssafyhome.model.dto.ReviewSearchDto;
import com.ssafyhome.model.dto.entity.mysql.ReviewEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
		name = "Review Controller",
		description = "부동산 매물에 대한 평점 및 평가 부여"
)
@RestController
@RequestMapping("/review")
public class ReviewController {

	@Operation(
			summary = "",
			description = ""
	)
	@PostMapping("/")
	public ResponseEntity<?> registerReview(
			@RequestBody
			ReviewEntity reviewEntity
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@GetMapping("/list")
	public ResponseEntity<List<ReviewEntity>> getReview(
			@RequestBody
			ReviewSearchDto reviewSearchDto
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@PutMapping("/{reviewSeq}")
	public ResponseEntity<?> updateReview(
			@PathVariable
			String reviewSeq,

			@RequestBody
			ReviewEntity reviewEntity
	) {

		return null;
	}

	@Operation(
			summary = "",
			description = ""
	)
	@DeleteMapping("/{reviewSeq}")
	public ResponseEntity<?> deleteReview(
			@PathVariable
			String reviewSeq
	) {

		return null;
	}
}
