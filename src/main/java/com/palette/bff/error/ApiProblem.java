package com.palette.bff.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public final class ApiProblem {

    private ApiProblem() {
    }

    public static ProblemDetail of(HttpStatusCode status, String title, String detail, String correlationId) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setProperty("correlationId", correlationId);
        return problem;
    }
}
