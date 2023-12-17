package com.golden_raspberry_awards.api.config.exceptions;

import com.golden_raspberry_awards.api.application.core.domain.exception.ErrorBodyResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Provider
@Slf4j
public class CustomApiExceptionHandler implements ExceptionMapper<CustomApiException> {
    @Override
    public Response toResponse(CustomApiException e) {
        ErrorBodyResponse error = new ErrorBodyResponse(e.getMessage(), e.getStatus(), e.getDetail(), new Date().toString());

        log.error("An error occurred with your request, detail: {}", error);

        return Response.status(e.getStatus()).entity(error).build();
    }
}
