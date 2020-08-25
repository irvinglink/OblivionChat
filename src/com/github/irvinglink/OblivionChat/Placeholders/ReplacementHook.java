package com.github.irvinglink.OblivionChat.Placeholders;

import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.entity.Player;

public class ReplacementHook {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);

    public String replace(Player player, Player target, String next) {
        return replace(player, target, null, next);
    }

    public String replace(Player player, Player target, Channel channel, String next) {

        switch (next) {

            case "player_displayname":

                if (player != null) return player.getDisplayName();

            case "target_displayname":

                if (target != null) return target.getDisplayName();

            case "player_name":

                if (player != null) return player.getName();

            case "target_name":

                if (target != null) return target.getName();

            case "prefix":
                return plugin.getLang().getString("Plugin_Prefix");

            case "channel_name":
                return channel.getChannelName();

            default:
                return null;
        }

    }
}
