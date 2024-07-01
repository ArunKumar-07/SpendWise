//package com.project.expensetracker.config;
//
//import com.project.expensetracker.util.Jwtutils;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final Jwtutils jwtutils;
//
//    public JwtRequestFilter(Jwtutils jwtutils) {
//        this.jwtutils = jwtutils;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        if (request.getRequestURI().equals("/new/signup") || request.getRequestURI().equals("/new/login")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        final String requestTokenHeader = request.getHeader("Authorization");
//        System.out.println("validate token process : ");
//        if (requestTokenHeader == null ) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            System.out.println("Authorization header is missing or invalid");
//            response.getWriter().write("Authorization header is missing or invalid");
//            return;
//        }
//
//        String jwtToken = requestTokenHeader.substring(7);
//        try {
//            String username = jwtutils.extractUsername(jwtToken);
//            if (username != null && jwtutils.validateToken(jwtToken, username)) {
//                System.out.println("token is success");
//                request.setAttribute("username", username);
//                chain.doFilter(request, response);
//            } else {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                System.out.println("Invalid or expired token");
//                response.getWriter().write("Invalid or expired token");
//            }
//        } catch (Exception e) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            System.out.println("Error processing token");
//            response.getWriter().write("Error processing token: " + e.getMessage());
//        }
//    }
//}
