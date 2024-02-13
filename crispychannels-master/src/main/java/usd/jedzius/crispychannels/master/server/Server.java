package usd.jedzius.crispychannels.master.server;

import com.esotericsoftware.minlog.Log;

public class Server {
    private final int id;
    private final String name;
    private boolean active;

    public Server(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
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
