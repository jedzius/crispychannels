package usd.jedzius.crispychannels.protocol.client;

public final class ProtocolClientBuilder {
    private int portTCP;
    private int portUDP;

    private ProtocolClientBuilder() {
    }

    public static ProtocolClientBuilder newBuilder() {
        return new ProtocolClientBuilder();
    }

    public ProtocolClientBuilder withPortTCP(int portTCP) {
        this.portTCP = portTCP;
        return this;
    }

    public ProtocolClientBuilder withPortUDP(int portUDP) {
        this.portUDP = portUDP;
        return this;
    }

    public ProtocolClient build() {
        return new ProtocolClient(portTCP, portUDP);
    }
}
