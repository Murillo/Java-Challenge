package com.revolut.bank.controller;

import com.revolut.bank.customer.form.CustomerForm;
import com.revolut.bank.customer.service.CustomerService;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Singleton
@Lock(LockType.READ)
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerService customerService;

    @GET
    public Response getAll() {
        return Response.ok(customerService.getAll()).build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") Long id) {
        return Response.ok(customerService.getCustomer(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, CustomerForm customerForm) {
        customerService.update(id, customerForm);
        return Response.ok().build();
    }

    @POST
    public Response create(CustomerForm customerForm) {
        customerService.create(customerForm);
        return Response.created(URI.create("/customer")).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        customerService.delete(id);
        return Response.ok().build();
    }
}