package com.sprk.spring_security_demo.filter;

import com.sprk.spring_security_demo.service.CustomUserDetailsService;
import com.sprk.spring_security_demo.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        // If the path is public, skip JWT validation
        if (path.equals("/") || path.equals("/welcome") || path.equals("/signup") || path.equals("/generate-token")) {
            filterChain.doFilter(request, response); // move to next filter
            return;
        }
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            System.out.println("\n\nAUTH HEADER -> "+authHeader);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // TOKEN
                username = jwtService.extractUsername(token);
            }

            System.out.println(token + " " + username);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                System.out.println("Searching For User If Token is Not Expired");
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                // Validate Token
                if(jwtService.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);

        }catch (ExpiredJwtException ex) {
            // Token is expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token expired. Please login again.\"}");
        } catch (JwtException ex) {
            // Any other JWT-related exception (invalid signature, malformed, etc.)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid token.\"}");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
