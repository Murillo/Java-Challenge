package com.revolut.bank.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BusinessRuleExceptionMapper implements ExceptionMapper<BusinessRuleException> {

    @Override
    public Response toResponse(BusinessRuleException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ExceptionMessage.of(exception.getMessage()))
                .build();
    }

}
