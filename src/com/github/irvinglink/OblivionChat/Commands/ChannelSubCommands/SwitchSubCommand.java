package com.github.irvinglink.OblivionChat.Commands.ChannelSubCommands;

import com.github.irvinglink.OblivionChat.Commands.Builders.SubCommand;
import com.github.irvinglink.OblivionChat.Exceptions.ChannelLimitException;
import com.github.irvinglink.OblivionChat.Exceptions.ChannelNoExistsException;
import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.Handlers.Chat;
import com.github.irvinglink.OblivionChat.Handlers.PlayerChannel;
import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SwitchSubCommand extends SubCommand {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);
    private final Chat chat = plugin.getChat();

    private final FileConfiguration lang = plugin.getLang();

    @Override
    public String getName() {
        return "switch";
    }

    @Override
    public String getDescription() {
        return lang.getString("Help.Switch_Description");
    }

    @Override
    public String getSyntax() {
        return "/channel switch <channel>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 2) {

            String channelName = args[1];

            Channel channel = Channel.getChannel(channelName);

            assert channel != null;

            try {

                if (channel.exists()) {

                    if (!channel.equals(PlayerChannel.getPlayerChannel(player.getUniqueId()))) {
                        try {
                            String permission = channel.getPermission();

                            if (player.hasPermission(permission)) {

                                player.sendMessage(chat.replace(player, channel, lang.getString("Channel.Join_Target"), true));
                                channel.switchChannel(player.getUniqueId());

                            } else
                                player.spigot().sendMessage(chat.hoverMessage(chat.replace(player, channel, lang.getString("Exceptions.Channel_No_Access"), true), permission));

                        } catch (ChannelLimitException e) {
                            player.sendMessage(chat.replace(player, channel, e.getMessage(), true));
                        }

                    } else
                        player.sendMessage(chat.replace(player, channel, lang.getString("Channel.Already_In_Channel_Player"), true));

                }

            } catch (ChannelNoExistsException e) {
                player.sendMessage(chat.replace(player, channel, e.getMessage(), true));
            }


        } else
            player.sendMessage(chat.replace(player, lang.getString("Syntax_Error"), true).replaceAll("%oblivion_command_syntax%", getSyntax()));
    }
}
