package usd.jedzius.crispychannels.bungee;

import com.esotericsoftware.minlog.Log;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bungee.YamlBungeeConfigurer;
import net.md_5.bungee.api.plugin.Plugin;
import usd.jedzius.crispychannels.bungee.config.ServerConfig;
import usd.jedzius.crispychannels.bungee.handler.payload.client.ClientAuthorizationPayloadHandler;
import usd.jedzius.crispychannels.bungee.handler.payload.client.ClientBindPayloadHandler;
import usd.jedzius.crispychannels.bungee.handler.payload.user.UserTransferInfoHandler;
import usd.jedzius.crispychannels.bungee.server.ServerService;
import usd.jedzius.crispychannels.common.initializer.Initializer;
import usd.jedzius.crispychannels.common.protocol.ProtocolClassIdentifier;
import usd.jedzius.crispychannels.protocol.server.ProtocolServer;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerBuilder;
import usd.jedzius.crispychannels.protocol.server.ProtocolServerWorker;

import java.io.File;
import java.util.List;

public class MasterServerPlugin extends Plugin implements Initializer<ProtocolServer> {

    private ProtocolServer server;
    private ServerService serverService;

    private ServerConfig config;

    @Override
    public void onLoad() {
        this.config = ConfigManager.create(ServerConfig.class, config -> {
            config.withConfigurer(new YamlBungeeConfigurer());
            config.withBindFile(new File(this.getDataFolder(), "config.yml"));
            config.saveDefaults();
            config.load();
        });
    }

    @Override
    public void onEnable() {
        this.serverService = new ServerService();
        this.prepareConnection();
    }

    @Override
    public void onDisable() {
        this.server.close();
    }

    @Override
    public void prepareConnection() {
        this.server = ProtocolServerBuilder.newBuilder()
                .withPortTCP(21111)
                .withPortUDP(21112)
                .build();

        this.server.initializer(this);

        final ProtocolServerWorker serverWorker = new ProtocolServerWorker(this, List.of(
                new UserTransferInfoHandler(),
                new ClientAuthorizationPayloadHandler(this.serverService, config),
                new ClientBindPayloadHandler(this.serverService)
        ));

        final Thread serverThread = new Thread(serverWorker, "MASTER");
        serverThread.start();
    }

    @Override
    public void onDisconnect() {
        Log.info("Disconnected!");
    }

    @Override
    public void onConnect() {
        Log.info("Connected!");
    }

    @Override
    public ProtocolClassIdentifier get() {
        return this.server;
    }
}
