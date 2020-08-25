package com.github.irvinglink.OblivionChat.Handlers;


import java.util.HashMap;
import java.util.UUID;

public class PlayerChannel {

    public static HashMap<UUID, Channel> playerList = new HashMap<>();

    public static HashMap<UUID, Channel> getPlayerList() {
        return playerList;
    }

    public static void add(UUID uuid, Channel channel) {
        if (!playerList.containsKey(uuid)) playerList.put(uuid, channel);
    }

    public static void remove(UUID uuid) {
        playerList.remove(uuid);
    }

    public static Channel getPlayerChannel(UUID uuid) {
        return playerList.get(uuid);
    }

}
