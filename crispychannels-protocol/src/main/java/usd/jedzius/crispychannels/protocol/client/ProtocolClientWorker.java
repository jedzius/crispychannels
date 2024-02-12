package usd.jedzius.crispychannels.protocol.client;

import usd.jedzius.crispychannels.protocol.payload.UserTransferPayload;
import usd.jedzius.crispychannels.protocol.serialization.EncodePayload;

import java.io.IOException;

public class ProtocolClientWorker implements Runnable {

    private final ProtocolClient client;

    public ProtocolClientWorker(ProtocolClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.client.start();
            this.client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
