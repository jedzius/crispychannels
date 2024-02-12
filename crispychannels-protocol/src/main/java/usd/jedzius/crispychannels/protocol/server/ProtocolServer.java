package usd.jedzius.crispychannels.protocol.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.protocol.handler.ProtocolServerHandler;
import usd.jedzius.crispychannels.protocol.payload.UserTransferPayload;

import java.io.IOException;

public class ProtocolServer {
    private final int portTCP;
    private final int portUDP;

    private Server protocolServer;

    public ProtocolServer(int portTCP, int portUDP) {
        this.portTCP = portTCP;
        this.portUDP = portUDP;
    }

    public void startNewServer() throws IOException {
        this.protocolServer = new Server();
        this.protocolServer.bind(this.portTCP, this.portUDP);
        this.protocolServer.start();

        this.protocolServer.getKryo().register(byte[].class);

        this.protocolServer.addListener(new ProtocolServerConnectionHandler());
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Master server enabled!");
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
