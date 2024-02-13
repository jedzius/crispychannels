package usd.jedzius.crispychannels.protocol.client;

import java.io.IOException;

public class ProtocolClientWorker implements Runnable {

    private final ProtocolClient client;
    private final int id;

    public ProtocolClientWorker(ProtocolClient client, int id) {
        this.client = client;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            this.client.start();
            this.client.connect();

            this.client.authorize(this.id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
