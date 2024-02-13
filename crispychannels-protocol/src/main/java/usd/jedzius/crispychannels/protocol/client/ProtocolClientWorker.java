package usd.jedzius.crispychannels.protocol.client;

import java.io.IOException;

public class ProtocolClientWorker implements Runnable {

    private final ProtocolClient client;
    private final int id;
    private final String name;

    public ProtocolClientWorker(ProtocolClient client, int id, String name) {
        this.client = client;
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            this.client.start();
            this.client.connect();

            this.client.bind(this.id, this.name);
            this.client.authorize(this.id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
