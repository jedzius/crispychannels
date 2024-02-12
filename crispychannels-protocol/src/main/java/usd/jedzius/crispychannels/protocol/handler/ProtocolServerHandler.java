package usd.jedzius.crispychannels.protocol.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.google.protobuf.Any;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public abstract class ProtocolServerHandler<T extends GeneratedMessage> implements Listener {

    private final T type;

    protected ProtocolServerHandler(T type) {
        this.type = type;
    }

    @SuppressWarnings(value = "unchecked")
    public void received(Connection connection, Object object) {
        if(!object.getClass().isAssignableFrom(byte[].class)) return;
        byte[] bytes = (byte[]) object;
        try {
            Any any  = Any.parseFrom(bytes);
            if(any.is(type.getClass())) {
                this.handle((T) any.unpack(type.getClass()));
            }
        } catch (InvalidProtocolBufferException e) {
            Log.error("Cannot deserialize bytes into right payload service!");
        }
    }

    public abstract void handle(T payload);
}
