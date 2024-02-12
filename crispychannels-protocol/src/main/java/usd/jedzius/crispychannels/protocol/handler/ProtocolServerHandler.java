package usd.jedzius.crispychannels.protocol.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public abstract class ProtocolServerHandler<T> implements Listener {

    private final Class<T> type;

    protected ProtocolServerHandler(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings(value = "unchecked")
    public void received(Connection connection, Object object) {
        if(object.getClass().isAssignableFrom(type)) {
            T t = (T) object;
            this.handle(t);
        }
    }

    public abstract void handle(T payload);
}
