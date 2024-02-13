package usd.jedzius.crispychannels.platform;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import usd.jedzius.crispychannels.platform.config.ClientConfig;
import usd.jedzius.crispychannels.protocol.client.ProtocolClient;
import usd.jedzius.crispychannels.protocol.client.ProtocolClientBuilder;
import usd.jedzius.crispychannels.protocol.client.ProtocolClientWorker;
import usd.jedzius.crispychannels.protocol.payload.UserTransferPayload;
import usd.jedzius.crispychannels.protocol.serialization.EncodePayload;

import java.io.File;

public class PlatformPlugin extends JavaPlugin {

    // Config
    private ClientConfig config;

    // Client
    private ProtocolClient client;

    @Override
    public void onLoad() {
        this.config = ConfigManager.create(ClientConfig.class, config -> {
            config.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            config.withBindFile(new File(this.getDataFolder(), "config.yml"));
            config.saveDefaults();
            config.load();
        });
    }

    @Override
    public void onEnable() {
        this.client = ProtocolClientBuilder.newBuilder()
                .withPortTCP(this.config.TCP)
                .withPortUDP(this.config.UDP)
                .build();

        final ProtocolClientWorker clientWorker = new ProtocolClientWorker(this.client, this.config.ID);
        final Thread clientThread = new Thread(clientWorker, "CLIENT-" + this.config.ID);
        clientThread.start();

        getCommand("testuj").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
                UserTransferPayload.UserTransferInfoPayload payload = UserTransferPayload.UserTransferInfoPayload.newBuilder()
                        .setSlot(1)
                        .setFood(20)
                        .setGamemode("CREATIVE")
                        .build();

                client.sendTCP(EncodePayload.serializePayload(payload));
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
