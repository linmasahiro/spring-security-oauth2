package main.java.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * フィルタ
 *
 */
@Component
public final class X509CheckFilter extends OncePerRequestFilter {

	public X509CheckFilter() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("xxxxxxxxxxxxxxxxxxxxx filter 2 xxxxxxxxxxxxxxxxx");

		// フィルタ処理完了、次に渡す
		filterChain.doFilter(request, response);
	}
}
