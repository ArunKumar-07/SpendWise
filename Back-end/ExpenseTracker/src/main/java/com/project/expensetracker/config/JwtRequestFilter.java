package com.project.expensetracker.config;


import com.project.expensetracker.util.Jwtutils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public JwtRequestFilter(Jwtutils jwtutils) {
        this.jwtutils = jwtutils;
    }

    private Jwtutils jwtutils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            username = jwtutils.extractUsername(jwtToken);
        }
        if (username != null && jwtToken != null && jwtutils.validateToken(jwtToken, username)) {
            // Token is valid. Set the user in SecurityContext
            // You might use Spring Security to set the user in SecurityContext instead
            request.setAttribute("username", username);
            chain.doFilter(request, response);
        } else {
            // Token is not valid
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized access");
            return;
        }
    }
}
