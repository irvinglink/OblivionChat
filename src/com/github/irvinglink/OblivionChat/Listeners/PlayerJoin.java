package com.github.irvinglink.OblivionChat.Listeners;

import com.github.irvinglink.OblivionChat.Exceptions.ChannelLimitException;
import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        try {
            Channel.getDefaultChannel().addMember(uuid);
        } catch (ChannelLimitException e) {
            player.sendMessage(plugin.getChat().str("&c&l" + e.getMessage()));
        }

    }
}
