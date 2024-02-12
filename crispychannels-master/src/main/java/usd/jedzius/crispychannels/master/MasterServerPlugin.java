package usd.jedzius.crispychannels.master;

import net.md_5.bungee.api.plugin.Plugin;
import usd.jedzius.crispychannels.master.handler.payload.UserTransferInfoHandler;
import usd.jedzius.crispychannels.protocol.server.ProtocolServer;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerBuilder;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerWorker;

import java.util.List;

public class MasterServerPlugin extends Plugin {

    private ProtocolServer server;

    @Override
    public void onEnable() {
        this.server = ProtocolServerBuilder.newBuilder()
                .withPortTCP(21111)
                .withPortUDP(21112)
                .build();

        final ProtocolServerWorker serverWorker = new ProtocolServerWorker(this.server, List.of(new UserTransferInfoHandler()));
        final Thread serverThread = new Thread(serverWorker, "MASTER");
        serverThread.start();
    }

    @Override
    public void onDisable() {
        this.server.close();
    }
}
