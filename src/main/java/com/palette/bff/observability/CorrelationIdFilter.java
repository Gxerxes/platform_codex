package com.palette.bff.observability;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Correlation-ID";
    public static final String MDC_KEY = "correlationId";
    public static final String CORRELATION_ID_ATTRIBUTE = "palette.correlationId";

    private static final Pattern SAFE_ID = Pattern.compile("^[A-Za-z0-9._~-]{1,64}$");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String correlationId = resolveCorrelationId(request);
        request.setAttribute(CORRELATION_ID_ATTRIBUTE, correlationId);
        response.setHeader(HEADER_NAME, correlationId);
        MDC.put(MDC_KEY, correlationId);

        long startedAt = System.nanoTime();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
            logger.info("request method=" + request.getMethod()
                    + " path=" + request.getRequestURI()
                    + " status=" + response.getStatus()
                    + " durationMs=" + durationMs);
            MDC.remove(MDC_KEY);
        }
    }

    private String resolveCorrelationId(HttpServletRequest request) {
        String supplied = request.getHeader(HEADER_NAME);
        if (supplied != null && SAFE_ID.matcher(supplied).matches()) {
            return supplied;
        }
        return UUID.randomUUID().toString();
    }
}
