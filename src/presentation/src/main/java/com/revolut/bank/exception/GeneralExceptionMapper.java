package com.revolut.bank.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ExceptionMessage.of(exception.getMessage()))
                .build();
    }

}
