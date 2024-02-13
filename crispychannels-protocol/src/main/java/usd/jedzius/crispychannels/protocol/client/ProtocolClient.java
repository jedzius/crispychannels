package usd.jedzius.crispychannels.protocol.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.protocol.payload.ClientAuthorizationPayload;
import usd.jedzius.crispychannels.protocol.payload.ClientBindServerProto;
import usd.jedzius.crispychannels.protocol.serialization.EncodePayload;

import java.io.IOException;
import java.util.concurrent.Executors;

public class ProtocolClient {
    private final int portTCP;
    private final int portUDP;

    private boolean ready;

    private Client protocolClient;
    public ProtocolClient(int portTCP, int portUDP) {
        this.portTCP = portTCP;
        this.portUDP = portUDP;
    }

    public void start() throws IOException {
        this.protocolClient = new Client();
        this.protocolClient.start();

        this.protocolClient.getKryo().register(byte[].class);

        this.protocolClient.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                setReady(true);
            }
        });
    }

    public void authorize(int id) {
        ClientAuthorizationPayload.ClientAuthorizationRequestPayload payload = ClientAuthorizationPayload.ClientAuthorizationRequestPayload.newBuilder()
                .setId(id)
                .build();

        this.sendTCP(EncodePayload.serializePayload(payload));
    }

    public void bind(int id, String name) {
        ClientBindServerProto.ClientBindServerInfoProto payload = ClientBindServerProto.ClientBindServerInfoProto.newBuilder()
                .setId(id)
                .setName(name)
                .build();

        this.sendTCP(EncodePayload.serializePayload(payload));
    }

    public void close() {
        this.protocolClient.close();
    }

    public void connect() throws IOException {
        this.protocolClient.connect(10000, "localhost", this.portTCP, this.portUDP);
    }

    public void sendTCP(byte[] value) {
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] The payload has been sent!");
        this.protocolClient.sendTCP(value);
    }

    public Client getProtocolClient() {
        return protocolClient;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public int getPortUDP() {
        return portUDP;
    }

    public int getPortTCP() {
        return portTCP;
    }
}
