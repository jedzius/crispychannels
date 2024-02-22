package usd.jedzius.crispychannels.protocol.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.common.initializer.Initializer;
import usd.jedzius.crispychannels.common.protocol.ProtocolClassIdentifier;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerConnectionHandler;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

import java.io.IOException;

public class ProtocolServer extends ProtocolClassIdentifier {
    private Server protocolServer;
    private Initializer<?> initializer;

    public ProtocolServer(int portUDP, int portTCP) {
        super(portUDP, portTCP);
    }

    @Override
    public void initializer(Initializer<?> initializer) {
        this.initializer = initializer;
    }

    @Override
    public void connect() throws IOException {
        this.initializer = initializer;
        this.protocolServer = new Server();
        this.protocolServer.bind(this.getPortTCP(), this.getPortUDP());
    }

    public void start() {
        this.protocolServer.start();
        this.protocolServer.getKryo().register(byte[].class);
        this.protocolServer.addListener(new ProtocolServerConnectionHandler(this));
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Master server enabled!");
    }

    public void sendTCPToAllConnections(byte[] value) {
        this.getProtocolServer().getConnections().forEach(conn -> conn.sendTCP(value));
    }

    public void close() {
        this.protocolServer.close();
    }

    public void bindHandler(ProtocolServerHandler handler) {
        this.protocolServer.addListener(handler);
    }

    public Server getProtocolServer() {
        return protocolServer;
    }

    @Override
    public Initializer<?> getInitializer() {
        return initializer;
    }
}
