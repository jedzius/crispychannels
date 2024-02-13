package usd.jedzius.crispychannels.master;

import net.md_5.bungee.api.plugin.Plugin;
import usd.jedzius.crispychannels.master.handler.payload.client.ClientAuthorizationPayloadHandler;
import usd.jedzius.crispychannels.master.handler.payload.client.ClientBindPayloadHandler;
import usd.jedzius.crispychannels.master.handler.payload.user.UserTransferInfoHandler;
import usd.jedzius.crispychannels.master.server.ServerService;
import usd.jedzius.crispychannels.protocol.server.ProtocolServer;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerBuilder;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerWorker;

import java.util.List;

public class MasterServerPlugin extends Plugin {

    private ProtocolServer server;
    private ServerService serverService;

    @Override
    public void onEnable() {
        this.serverService = new ServerService();

        this.server = ProtocolServerBuilder.newBuilder()
                .withPortTCP(21111)
                .withPortUDP(21112)
                .build();

        final ProtocolServerWorker serverWorker = new ProtocolServerWorker(this.server, List.of(
                new UserTransferInfoHandler(),
                new ClientAuthorizationPayloadHandler(this.serverService),
                new ClientBindPayloadHandler(this.serverService)
        ));

        final Thread serverThread = new Thread(serverWorker, "MASTER");
        serverThread.start();
    }

    @Override
    public void onDisable() {
        this.server.close();
    }
}
