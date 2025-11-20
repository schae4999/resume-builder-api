package in.joyhong.resumebuilderapi.security;

import in.joyhong.resumebuilderapi.document.User;
import in.joyhong.resumebuilderapi.repository.UserRepository;
import in.joyhong.resumebuilderapi.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // Validate the JWT for every request user makes after login
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userId = null;

        // "Bearer" means authorization header contains JWT
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            // removes "Bearer" prefix and keeps only the JWT
            token = authHeader.substring(7);
            try {
                userId = jwtUtil.getUserIdFromToken(token);

            } catch (Exception e) {
                log.error("Token is not valid/available.");
            }
        }

        // Avoids duplicate work if something is already authenticated in the SecurityContext
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)) {
                    User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found."));
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Mark the request as "authenticated" in Spring
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                log.error("Exception occurred while validation the token.");
            }
        }
        filterChain.doFilter(request, response);
    }
}
