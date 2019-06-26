package main.java.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * フィルタ1
 */
@Component
public final class SystemIdCheckFilter extends OncePerRequestFilter {

	public SystemIdCheckFilter() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("xxxxxxxxxxxxxxxxxxxxx filter 1 xxxxxxxxxxxxxxxxx");

		// フィルタ処理完了、次に渡す
		filterChain.doFilter(request, response);
	}
}
