package com.ssafyhome.handler.accessdeniedhandler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class AccessDeniedAspect {

	@Around("@annotation(AccessDeniedHandler)")
	public Object handleAccessDenied(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (AccessDeniedException e) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("접근 권한이 없습니다");
		}
	}
}
