package com.revolut.bank.controller;

import com.revolut.bank.currentaccount.dao.CurrentAccountDao;
import com.revolut.bank.currentaccount.form.CreateCurrentAccountForm;
import com.revolut.bank.currentaccount.form.TransferBetweenAccountForm;
import com.revolut.bank.currentaccount.service.CurrentAccountService;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


@Singleton
@Lock(LockType.READ)
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CurrentAccountController {

    @Inject
    CurrentAccountService currentAccountService;

    @Inject
    CurrentAccountDao currentAccountDao;

    @GET
    @Path("/all")
    public Response getAll() {
        return Response.ok(currentAccountDao.getAll()).build();
    }

    @POST
    public Response create(CreateCurrentAccountForm currentAccountForm) {
        currentAccountService.create(currentAccountForm);
        return Response.created(URI.create("/account")).build();
    }

    @PUT
    @Path("/{accountCode}/withdraw")
    public Response withdraw(@PathParam("accountCode") Long accountCode,
                             @QueryParam("quantity") Double quantity) {
        currentAccountService.withdraw(accountCode, quantity);
        return Response.ok().build();
    }

    @PUT
    @Path("/{accountCode}/deposit")
    public Response deposit(@PathParam("accountCode") Long accountCode,
                            @QueryParam("quantity") Double quantity) {
        currentAccountService.deposit(accountCode, quantity);
        return Response.ok().build();
    }

    @PUT
    @Path("/{accountCode}/transfer")
    public Response transfer(@PathParam("accountCode") Long id,
                             TransferBetweenAccountForm transferBetweenAccountForm) {
        currentAccountService.transfer(id, transferBetweenAccountForm);
        return Response.ok().build();
    }

    @GET
    @Path("/{accountCode}/balance")
    public Response balance(@PathParam("accountCode") Long accountCode) {
        return Response.ok(currentAccountService.balance(accountCode)).build();
    }

}
