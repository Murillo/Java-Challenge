package com.revolut.bank.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RecordNotFoundExceptionMapper implements ExceptionMapper<RecordNotFoundException> {

    @Override
    public Response toResponse(RecordNotFoundException exception) {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(ExceptionMessage.of(exception.getMessage()))
                .build();
        }
}
