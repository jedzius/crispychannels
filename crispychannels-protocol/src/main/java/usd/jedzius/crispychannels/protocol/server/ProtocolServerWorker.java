package usd.jedzius.crispychannels.protocol.server;

import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProtocolServerWorker implements Runnable {

    private final ProtocolServer protocolServer;
    private final List<ProtocolServerHandler> handlerList;

    public ProtocolServerWorker(ProtocolServer protocolServer, List<ProtocolServerHandler> handlerList) {
        this.protocolServer = protocolServer;
        this.handlerList = handlerList;
    }

    @Override
    public void run() {
        try {
            this.protocolServer.connect();
            this.protocolServer.start();
            this.handlerList.stream().filter(Objects::nonNull).forEach(this.protocolServer::bindHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
