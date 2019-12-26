package com.revolut.money.infrastructure;

import com.revolut.money.controller.account.AccountController;
import com.revolut.money.controller.transaction.TransactionController;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerFactory {

    public static Server newJettyServer(int port) {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        String controllers = String.join(",",
                TransactionController.class.getCanonicalName(),
                AccountController.class.getCanonicalName()
        );

        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", controllers);
        jerseyServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        return server;
    }
}
