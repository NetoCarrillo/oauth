package net.netosoft.oauth.monolite.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static net.netosoft.oauth.monolite.security.SecurityConstants.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest req,
						 ServletResponse res,
						 FilterChain chain)
				throws IOException, ServletException{
		
		final HttpServletResponse response = (HttpServletResponse)res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Max-Age", TOKEN_MAX_AGE);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", ALLOW_METHODS);
		response.setHeader("Access-Control-Allow-Headers", ALLOW_HEADERS);
		if("OPTIONS".equalsIgnoreCase(((HttpServletRequest)req).getMethod())){
			response.setStatus(HttpServletResponse.SC_OK);
		}else{
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy(){
		/**
		 * Since there is no need to release any source this method has no use.
		 */
	}

	@Override
	public void init(FilterConfig config) throws ServletException{
		/**
		 * Since there is no need to initialize this filter this method has no use.
		 */
	}
}
