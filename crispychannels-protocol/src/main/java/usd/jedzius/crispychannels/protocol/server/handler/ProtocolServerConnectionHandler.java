package usd.jedzius.crispychannels.protocol.server.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import usd.jedzius.crispychannels.protocol.server.ProtocolServer;

public class ProtocolServerConnectionHandler implements Listener {

    private final ProtocolServer protocolServer;

    public ProtocolServerConnectionHandler(ProtocolServer protocolServer) {
        this.protocolServer = protocolServer;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object.getClass().isAssignableFrom(FrameworkMessage.KeepAlive.class)) return;
    }

    @Override
    public void connected(Connection connection) {
        this.protocolServer.getInitializer().onConnect();
    }


    @Override
    public void disconnected(Connection connection) {
        this.protocolServer.getInitializer().onDisconnect();
    }
}
