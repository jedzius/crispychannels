package usd.jedzius.crispychannels.protocol.server;

public final class ProtocolServerBuilder {
    private int portTCP;
    private int portUDP;

    private ProtocolServerBuilder() {
    }

    public static ProtocolServerBuilder newBuilder() {
        return new ProtocolServerBuilder();
    }

    public ProtocolServerBuilder withPortTCP(int portTCP) {
        this.portTCP = portTCP;
        return this;
    }

    public ProtocolServerBuilder withPortUDP(int portUDP) {
        this.portUDP = portUDP;
        return this;
    }

    public ProtocolServer build() {
        return new ProtocolServer(portTCP, portUDP);
    }
}
