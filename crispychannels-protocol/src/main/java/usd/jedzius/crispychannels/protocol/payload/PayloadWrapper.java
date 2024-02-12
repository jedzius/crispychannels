package usd.jedzius.crispychannels.protocol.payload;

public abstract class PayloadWrapper {
    private final byte bytes;

    protected PayloadWrapper(byte bytes) {
        this.bytes = bytes;
    }

    public byte getBytes() {
        return bytes;
    }

    public Class<? extends PayloadWrapper> getType() {
        return this.getClass();
    }
}
