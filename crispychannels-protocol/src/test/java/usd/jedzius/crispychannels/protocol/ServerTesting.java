package usd.jedzius.crispychannels.protocol;

import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.protocol.handler.ProtocolServerHandler;
import usd.jedzius.crispychannels.protocol.payload.UserTransferPayload;
import usd.jedzius.crispychannels.protocol.server.ProtocolServer;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerBuilder;

import java.io.IOException;

public class ServerTesting {
    public static void main(String[] args) {
        ProtocolServer server = ProtocolServerBuilder.newBuilder()
                .withPortTCP(21111)
                .withPortUDP(21112)
                .build();

        Thread thread = new Thread(() -> {
            try {
                server.startNewServer();
                server.bindHandler(new TestHandler());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        thread.setName("MASTER");
        thread.start();
    }

    public static class TestHandler extends ProtocolServerHandler<UserTransferPayload.UserTransferInfoPayload> {

        protected TestHandler() {
            super(UserTransferPayload.UserTransferInfoPayload.class);
        }

        @Override
        public void handle(UserTransferPayload.UserTransferInfoPayload payload) {
            Log.info("UDALO SIE PAKIET DOSZEDL!" + payload.getSlot());
        }
    }
}
