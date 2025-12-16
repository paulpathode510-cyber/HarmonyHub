package com.soft.filters;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.soft.service.JwtService;
import com.soft.service.UserDetailsServiceImpl;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. If header is missing or does not start with Bearer → skip filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract JWT token (remove "Bearer ")
        String jwt = authHeader.substring(7);

        // 4. Extract username (email) from token
        String username = jwtService.extractUsername(jwt);

        // 5. Continue only if user is not already authenticated
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Load user details from DB
            var userDetails = userDetailsService.loadUserByUsername(username);

            // 7. Validate token
            if (jwtService.validateToken(jwt)) {

                // 8. Extract role from token
                String role = jwtService.extractRole(jwt);

                // 9. Convert role into Spring Security authority
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + role);

                // 10. Create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(authority)
                        );

                // 11. Attach request details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 12. Set authentication into security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 13. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
