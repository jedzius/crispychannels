package usd.jedzius.crispychannels.protocol.server;

import usd.jedzius.crispychannels.common.initializer.Initializer;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProtocolServerWorker implements Runnable {

    private final Initializer<?> initializer;
    private final List<ProtocolServerHandler<?>> handlerList;

    public ProtocolServerWorker(Initializer<?> initializer, List<ProtocolServerHandler<?>> handlerList) {
        this.initializer = initializer;
        this.handlerList = handlerList;
    }

    @Override
    public void run() {
        try {
            final ProtocolServer server = (ProtocolServer) this.initializer.get();

            server.connect();
            server.start();

            this.handlerList.stream().filter(Objects::nonNull).forEach(server::bindHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
