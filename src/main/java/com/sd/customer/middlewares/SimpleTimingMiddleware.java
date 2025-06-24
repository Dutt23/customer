package com.sd.customer.middlewares;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SimpleTimingMiddleware implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTimingMiddleware.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Started request [{} {}]", req.getMethod(), req.getRequestURI());
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - start;
        logger.info("Request [{} {}] completed in {} ms", req.getMethod(), req.getRequestURI(), duration);
    }
}
