package com.github.irvinglink.OblivionChat.Listeners;

import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.Handlers.PlayerChannel;
import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuit implements Listener {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Channel channel = PlayerChannel.getPlayerChannel(uuid);

        channel.removeMember(uuid);

    }
}
