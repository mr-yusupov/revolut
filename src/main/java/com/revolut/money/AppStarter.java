package com.revolut.money;

import org.eclipse.jetty.server.Server;

import static com.revolut.money.infrastructure.ServerFactory.newJettyServer;

public class AppStarter {
    public static final int PORT = 8888;
    private Server server;

    public static void main(String[] args) throws Exception {
        new AppStarter().start(PORT, new DataProviderFactory());
    }

    public void start(int port, DataProvider provider) throws InterruptedException {
        provider.prepareSampleData();

        Thread serverThread = prepareServerThread(port);
        serverThread.start();
        serverThread.join();
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    private Thread prepareServerThread(int port) {
        return new Thread(() -> {
            try {
                server = newJettyServer(port);
                server.start();
            } catch (Exception e) {
                //TODO put logging for e
                if (server != null) {
                    try {
                        server.stop();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    } finally {
                        server.destroy();
                    }
                }
            }
        });
    }
}
