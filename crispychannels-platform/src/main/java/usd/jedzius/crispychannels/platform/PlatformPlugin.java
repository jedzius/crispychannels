package usd.jedzius.crispychannels.platform;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import usd.jedzius.crispychannels.protocol.UserTransferPayload;
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
                .withPortTCP(11115)
                .withPortUDP(11110)
                .build();

        final ProtocolClientWorker clientWorker = new ProtocolClientWorker(this.client);
        final Thread clientThread = new Thread(clientWorker, "CLIENT-" + ID);
        clientThread.start();

        this.getCommand("testuj").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
                UserTransferPayload.UserConnectServerPayload payload = UserTransferPayload.UserConnectServerPayload.newBuilder()
                        .setSlot(1)
                        .build();

                byte[] payloadBytes = payload.toByteArray();
                client.sendTCP(payloadBytes);
                return false;
            }
        });
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
