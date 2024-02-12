package usd.jedzius.crispychannels.protocol.serialization;

import com.google.protobuf.Any;
import com.google.protobuf.GeneratedMessage;

public class EncodePayload {
    public static <T extends GeneratedMessage> byte[] serializePayload(T type) {
        Any any = Any.pack(type);
        return any.toByteArray();
    }
}
