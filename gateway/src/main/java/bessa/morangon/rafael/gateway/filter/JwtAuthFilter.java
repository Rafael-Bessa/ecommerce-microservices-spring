package bessa.morangon.rafael.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    private static final List<String> PUBLIC_ROUTES = List.of(
            "/auth/login",
            "/auth/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Rota pública? Passa direto
        if (isPublic(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrai o header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, "Token não informado");
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Propaga os dados do usuário como headers para os serviços downstream
            // Os serviços só precisam ler esses headers — sem JWT neles
            var mutated = new HeaderMutatingRequest(request);
            mutated.addHeader("X-User-Email", claims.getSubject());
            mutated.addHeader("X-User-Id", String.valueOf(claims.get("userId")));
            mutated.addHeader("X-User-Role", String.valueOf(claims.get("role")));

            filterChain.doFilter(mutated, response);

        } catch (JwtException e) {
            sendError(response, "Token inválido ou expirado");
        }
    }

    private boolean isPublic(String path) {
        return PUBLIC_ROUTES.stream().anyMatch(path::startsWith);
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("""
            {"error": "%s", "status": 401}
            """.formatted(message));
    }
}