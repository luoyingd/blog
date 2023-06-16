package com.example.blog.api.config.xss;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class XssFilter implements Filter {
    List<String> ignoreXSSURIList = new ArrayList<>();

    public XssFilter() {
        // ignore filter here so that html request can be handled
        ignoreXSSURIList.add("/api/blog/addOrUpdate");
        ignoreXSSURIList.add("/api/common/photo/*");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!ignoreXSSURIList.contains(((HttpServletRequest) servletRequest).getRequestURI())) {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                    (HttpServletRequest) servletRequest);
            filterChain.doFilter(xssRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
