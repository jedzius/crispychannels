package usd.jedzius.crispychannels.common.initializer;

import usd.jedzius.crispychannels.common.protocol.ProtocolClassIdentifier;

public interface Initializer<T extends ProtocolClassIdentifier> {
    void prepareConnection();
    void onDisconnect();
    void onConnect();

    ProtocolClassIdentifier get();
}
