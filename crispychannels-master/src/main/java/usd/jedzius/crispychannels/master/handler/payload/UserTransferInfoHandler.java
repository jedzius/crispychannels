package usd.jedzius.crispychannels.master.handler.payload;

import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.protocol.payload.UserTransferPayload;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

public class UserTransferInfoHandler extends ProtocolServerHandler<UserTransferPayload.UserTransferInfoPayload> {
    public UserTransferInfoHandler() {
        super(UserTransferPayload.UserTransferInfoPayload.class);
    }

    @Override
    public void handle(UserTransferPayload.UserTransferInfoPayload payload) {
        Log.info("doszedl payload");
    }
}
