package com.revolut.money.controller.account;

import com.revolut.money.app.account.BalanceHandler;
import com.revolut.money.app.account.BalanceHandlerFactory;
import com.revolut.money.app.account.TransferHandler;
import com.revolut.money.app.account.TransferHandlerFactory;
import com.revolut.money.controller.transferDto.TransferRequestDto;
import com.revolut.money.controller.transferDto.TransferResponseDto;
import com.revolut.money.domain.transfer.TransferResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ok;

@Path("/account")
public class AccountController {

    @GET
    @Path("/{accountId}/balance/")
    @Produces(TEXT_PLAIN)
    public Response getBalance(@PathParam("accountId") long accountId) {
        BalanceHandler handler = BalanceHandlerFactory.create();

        return ok().entity(handler.getBalance(accountId)).build();
    }

    @POST
    @Path("/transfer/")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response transfer(TransferRequestDto transfer) {
        TransferHandler handler = TransferHandlerFactory.create();

        TransferResult result = handler.transfer(transfer.getRequestId(), transfer.getSenderId(), transfer.getReceiverId(), transfer.getAmount());

        return ok().entity(new TransferResponseDto(result.isSuccessful(), result.getDetails())).build();
    }
}
