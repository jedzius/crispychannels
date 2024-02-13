package usd.jedzius.crispychannels.master.handler.payload.client;

import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.master.server.Server;
import usd.jedzius.crispychannels.master.server.ServerService;
import usd.jedzius.crispychannels.protocol.payload.ClientBindServerProto;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

public class ClientBindPayloadHandler extends ProtocolServerHandler<ClientBindServerProto.ClientBindServerInfoProto> {

    private final ServerService serverService;

    public ClientBindPayloadHandler(ServerService serverService) {
        super(ClientBindServerProto.ClientBindServerInfoProto.class);
        this.serverService = serverService;
    }

    @Override
    public void handle(ClientBindServerProto.ClientBindServerInfoProto payload) {
        this.serverService.add(new Server(payload.getId(), payload.getName()));
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Bound client with master! (" + payload.getId() + "/" + payload.getName()+")");
    }
}
