package usd.jedzius.crispychannels.common.protocol;

import usd.jedzius.crispychannels.common.initializer.Initializer;

import java.io.IOException;

public abstract class ProtocolClassIdentifier {
    private final int portUDP;
    private final int portTCP;

    protected ProtocolClassIdentifier(int portUDP, int portTCP) {
        this.portUDP = portUDP;
        this.portTCP = portTCP;
    }

    public abstract void initializer(Initializer<?> initializer);
    public abstract void connect() throws IOException;
    public abstract void start() throws IOException;
    public abstract Initializer<?> getInitializer();
    public int getPortTCP() {
        return portTCP;
    }
    public int getPortUDP() {
        return portUDP;
    }

}
