package com.github.irvinglink.OblivionChat.Listeners;

import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.Handlers.PlayerChannel;
import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class AsyncPlayerChat implements Listener {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Channel channel = PlayerChannel.getPlayerChannel(uuid);

        assert channel != null;

        channel.sendMessage(uuid, event.getMessage());

    }
}
