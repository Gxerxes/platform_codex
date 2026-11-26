package com.palette.bff.error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import com.palette.bff.observability.CorrelationIdFilter;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    ProblemDetail handleNotFound(NoResourceFoundException ex, HttpServletRequest request) {
        return problem(HttpStatus.NOT_FOUND, "Not Found", "The requested resource was not found.", request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ProblemDetail handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return problem(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", "The HTTP method is not supported for this resource.", request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ProblemDetail handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return problem(HttpStatus.BAD_REQUEST, "Bad Request", "A required request parameter is missing.", request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return problem(HttpStatus.BAD_REQUEST, "Bad Request", "One or more request fields are invalid.", request);
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unexpected request failure", ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "An unexpected error occurred.", request);
    }

    private ProblemDetail problem(HttpStatus status, String title, String detail, HttpServletRequest request) {
        String correlationId = (String) request.getAttribute(CorrelationIdFilter.CORRELATION_ID_ATTRIBUTE);
        if (correlationId == null) {
            correlationId = "unavailable";
        }
        return ApiProblem.of(status, title, detail, correlationId);
    }
}
