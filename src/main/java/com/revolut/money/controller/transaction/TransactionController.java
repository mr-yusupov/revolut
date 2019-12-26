package com.revolut.money.controller.transaction;

import com.revolut.money.app.transaction.TransactionHandlerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/transaction")
public class TransactionController {

    @POST
    @Produces(TEXT_PLAIN)
    public Response newTransaction() {
        String transactionId = TransactionHandlerFactory.create().createNewTransaction();

        return Response.ok().entity(transactionId).build();
    }

}
