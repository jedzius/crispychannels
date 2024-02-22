package usd.jedzius.crispychannels.protocol.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.common.initializer.Initializer;
import usd.jedzius.crispychannels.common.protocol.ProtocolClassIdentifier;
import usd.jedzius.crispychannels.protocol.payload.ClientAuthorizationPayload;
import usd.jedzius.crispychannels.protocol.payload.ClientBindServerProto;
import usd.jedzius.crispychannels.protocol.serialization.EncodePayload;

import java.io.IOException;

public class ProtocolClient extends ProtocolClassIdentifier {

    private boolean ready;

    private Client protocolClient;
    private Initializer<?> initializer;

    protected ProtocolClient(int portUDP, int portTCP) {
        super(portUDP, portTCP);
    }

    @Override
    public void initializer(Initializer<?> initializer) {
        this.initializer = initializer;
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

    @Override
    public Initializer<?> getInitializer() {
        return this.initializer;
    }

    public void authorize(int id, String code) {
        ClientAuthorizationPayload.ClientAuthorizationRequestPayload payload = ClientAuthorizationPayload.ClientAuthorizationRequestPayload.newBuilder()
                .setId(id)
                .setCode(code)
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
        this.protocolClient.connect(10000, "localhost", this.getPortTCP(), this.getPortUDP());
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
}
