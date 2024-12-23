//package com.example.ebookdemo.filter;// package com.jiawa.wiki.filter;
//
// import jakarta.servlet.*;
// import jakarta.servlet.http.HttpServletRequest;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
//
// import java.io.IOException;
//
// @Component
// public class LogFilter implements Filter {
//
//     private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);
//
//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {
//
//     }
//
//     @Override
//     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//         // 打印请求信息
//         HttpServletRequest request = (HttpServletRequest) servletRequest;
//         LOG.info("------------- LogFilter 开始 -------------");
//         LOG.info("过滤器请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
//         LOG.info("过滤器远程地址: {}", request.getRemoteAddr());
//
//         long startTime = System.currentTimeMillis();
//         filterChain.doFilter(servletRequest, servletResponse);
//         LOG.info("------------- 过滤器LogFilter 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
//     }
// }
