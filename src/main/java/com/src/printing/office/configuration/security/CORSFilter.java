package com.src.printing.office.configuration.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 *
 * @author Milan Plazinic
 */
@Component
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig fc) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpresponse = (HttpServletResponse) res;
		HttpServletRequest httpRequest = (HttpServletRequest) req;

		// ovde se prividja
		httpresponse.setHeader("Access-Control-Allow-Origin", "*");
		httpresponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpresponse.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
		httpresponse.setHeader("Access-Control-Max-Age", "3600");
		httpresponse.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Accept, Authorization, PO-TOKEN, responseType, Content-Disposition, Content-Length");
		httpresponse.setHeader("Access-Control-Expose-Headers", "PO-TOKEN, filename");

		if (!"OPTIONS".equals(httpRequest.getMethod())) {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}

}
