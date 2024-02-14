package usd.jedzius.crispychannels.master.server;

import com.esotericsoftware.minlog.Log;

public class Server {
    private final int id;
    private final String name;
    private boolean active;
    private int connectionId;

    public Server(int id,  String name) {
        this.id = id;
        this.connectionId = connectionId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        Log.info("[CHANNELS/" + Thread.currentThread().getName() +"] MasterServer has been connected with this client!");
    }
}
