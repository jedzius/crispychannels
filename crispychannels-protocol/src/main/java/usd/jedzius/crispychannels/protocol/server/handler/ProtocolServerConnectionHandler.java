package usd.jedzius.crispychannels.protocol.server.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class ProtocolServerConnectionHandler implements Listener {

    @Override
    public void received(Connection connection, Object object) {
        if(object.getClass().isAssignableFrom(FrameworkMessage.KeepAlive.class)) return;
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Received a payload from " + connection.getRemoteAddressTCP().getHostString());
    }

    @Override
    public void connected(Connection connection) {
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Received a connection from " + connection.getRemoteAddressTCP().getHostString());
    }


    @Override
    public void disconnected(Connection connection) {
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] Client (" + connection.getID() + "/" + (connection.getRemoteAddressTCP() == null ? "UNDEFINED" : connection.getRemoteAddressTCP().getHostString()) + ") disconnected!");
    }
}
