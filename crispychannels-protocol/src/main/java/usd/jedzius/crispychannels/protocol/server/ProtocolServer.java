package usd.jedzius.crispychannels.protocol.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerConnectionHandler;
import usd.jedzius.crispychannels.protocol.server.handler.ProtocolServerHandler;

import java.io.IOException;

public class ProtocolServer {
    private final int portTCP;
    private final int portUDP;

    private Server protocolServer;

    public ProtocolServer(int portTCP, int portUDP) {
        this.portTCP = portTCP;
        this.portUDP = portUDP;
    }

    public void connect() throws IOException {
        this.protocolServer = new Server();
        this.protocolServer.bind(this.portTCP, this.portUDP);
    }

    public void start() {
        this.protocolServer.start();
        this.protocolServer.getKryo().register(byte[].class);
        this.protocolServer.addListener(new ProtocolServerConnectionHandler());
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

    public int getPortTCP() {
        return portTCP;
    }

    public int getPortUDP() {
        return portUDP;
    }

    public Server getProtocolServer() {
        return protocolServer;
    }
}
