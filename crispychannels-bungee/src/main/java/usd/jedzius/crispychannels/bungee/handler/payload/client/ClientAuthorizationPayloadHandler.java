package usd.jedzius.crispychannels.bungee.handler.payload.client;

import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.bungee.config.ServerConfig;
import usd.jedzius.crispychannels.bungee.server.ServerService;
import usd.jedzius.crispychannels.protocol.authorization.ServerAuthorization;
import usd.jedzius.crispychannels.protocol.payload.ClientAuthorizationPayload;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

public class ClientAuthorizationPayloadHandler extends ProtocolServerHandler<ClientAuthorizationPayload.ClientAuthorizationRequestPayload> {

    private final ServerService serverService;
    private final ServerConfig config;

    public ClientAuthorizationPayloadHandler(ServerService serverService, ServerConfig config) {
        super(ClientAuthorizationPayload.ClientAuthorizationRequestPayload.class);
        this.serverService = serverService;
        this.config = config;
    }

    @Override
    public void handle(ClientAuthorizationPayload.ClientAuthorizationRequestPayload payload) {
        int id = payload.getId();

        this.serverService.find(id).ifPresentOrElse(server -> {
            String code = payload.getCode();
            System.out.println(code);
            if(ServerAuthorization.valid(this.config.AUTH_CODE, code)) {
                server.setConnectionId(this.getConnection().getID());
                server.setActive(true);
                Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Authorized a new client!");
                return;
            }
            Log.warn("[CHANNELS/" + Thread.currentThread().getName() +"] Client between server and client are not same!");
        }, () -> Log.warn("[CHANNELS/" + Thread.currentThread().getName() +"] Received client authorization request but the server with id " + id + " is not bound in master!"));
    }
}
