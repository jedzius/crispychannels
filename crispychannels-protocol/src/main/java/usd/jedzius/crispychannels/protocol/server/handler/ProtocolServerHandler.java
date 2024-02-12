package usd.jedzius.crispychannels.protocol.server.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public abstract class ProtocolServerHandler<T extends Message> implements Listener {

    private final Class<T> type;

    protected ProtocolServerHandler(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings(value = "unchecked")
    public void received(Connection connection, Object object) {
        if(!object.getClass().isAssignableFrom(byte[].class)) return;
        byte[] bytes = (byte[]) object;
        try {
            Any any  = Any.parseFrom(bytes);
            if(any.is(type)) {
                this.handle((T) any.unpack(type));
            }
        } catch (InvalidProtocolBufferException e) {
            Log.error("Cannot deserialize bytes into right payload service!");
        }
    }

    public abstract void handle(T payload);
}
