package usd.jedzius.crispychannels.protocol.serialization;

import com.google.protobuf.Any;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

public class EncodePayload {
    public static <T extends Message> byte[] serializePayload(T type) {
        Any any = Any.pack(type);
        return any.toByteArray();
    }
}
