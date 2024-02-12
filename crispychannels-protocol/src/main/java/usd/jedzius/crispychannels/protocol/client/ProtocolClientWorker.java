package usd.jedzius.crispychannels.protocol.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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
