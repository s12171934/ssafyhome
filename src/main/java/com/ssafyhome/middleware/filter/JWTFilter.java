package com.ssafyhome.middleware.filter;

import com.ssafyhome.model.dao.mapper.UserMapper;
import com.ssafyhome.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final UserMapper userMapper;

	public JWTFilter(
			JWTUtil jwtUtil,
			UserMapper userMapper
	) {

		this.jwtUtil = jwtUtil;
		this.userMapper = userMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String accessToken = request.getHeader("Authorization");
		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String category = jwtUtil.getKey(accessToken, "category");
		String userId = jwtUtil.getKey(accessToken, "userId");
		String userEmail = jwtUtil.getKey(accessToken, "userEmail");

		if (category.equals("access") ||
				userMapper.getUserBySeqAndEmail(userId, userEmail) == null
		) {

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(request, response);
	}
}
