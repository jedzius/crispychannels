package usd.jedzius.crispychannels.master.handler.payload.client;

import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.master.server.ServerService;
import usd.jedzius.crispychannels.protocol.payload.ClientAuthorizationPayload;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

public class ClientAuthorizationPayloadHandler extends ProtocolServerHandler<ClientAuthorizationPayload.ClientAuthorizationRequestPayload> {

    private final ServerService serverService;

    public ClientAuthorizationPayloadHandler(ServerService serverService) {
        super(ClientAuthorizationPayload.ClientAuthorizationRequestPayload.class);
        this.serverService = serverService;
    }

    @Override
    public void handle(ClientAuthorizationPayload.ClientAuthorizationRequestPayload payload) {
        int id = payload.getId();

        this.serverService.find(id).ifPresentOrElse(server -> server.setActive(true), () -> Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Received client authorization request but the server with id " + id + " is not configured in master!"));
    }
}
