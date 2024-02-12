package usd.jedzius.crispychannels.protocol;

import usd.jedzius.crispychannels.protocol.server.ProtocolServer;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerBuilder;

import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ProtocolServer server = ProtocolServerBuilder.newBuilder()
                .withPortTCP(11115)
                .withPortUDP(11110)
                .build();

        Thread thread = new Thread(() -> {
            try {
                server.startNewServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        thread.setName("MASTER");
        thread.start();
    }
}
