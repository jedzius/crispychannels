package usd.jedzius.crispychannels.platform;

import org.bukkit.plugin.java.JavaPlugin;
import usd.jedzius.crispychannels.protocol.client.ProtocolClient;
import usd.jedzius.crispychannels.protocol.client.ProtocolClientBuilder;
import usd.jedzius.crispychannels.protocol.client.ProtocolClientWorker;

public class PlatformPlugin extends JavaPlugin {

    private final static int ID = 1;

    // Client
    private ProtocolClient client;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        this.client = ProtocolClientBuilder.newBuilder()
                .withPortTCP(21111)
                .withPortUDP(21112)
                .build();

        final ProtocolClientWorker clientWorker = new ProtocolClientWorker(this.client);
        final Thread clientThread = new Thread(clientWorker, "CLIENT-" + ID);
        clientThread.start();
    }

    @Override
    public void onDisable() {
        this.client.close();
        Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);
    }

    public ProtocolClient getClient() {
        return client;
    }
}
