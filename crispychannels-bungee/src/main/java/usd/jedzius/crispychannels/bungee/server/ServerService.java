package usd.jedzius.crispychannels.bungee.server;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class ServerService {
    private final Map<Integer, Server> serverMap;

    public ServerService() {
        this.serverMap = Maps.newConcurrentMap();
    }

    public void add(Server server) {
        this.serverMap.put(server.getId(),server);
    }

    public Optional<Server> find(int id) {
        return Optional.ofNullable(this.serverMap.get(id));
    }
}
