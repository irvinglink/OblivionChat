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

public class JoinSubCommand extends SubCommand {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);
    private final Chat chat = plugin.getChat();

    private final FileConfiguration lang = plugin.getLang();

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return lang.getString("Help.Join_Description");
    }

    @Override
    public String getSyntax() {
        return "/channel join <player> <channel>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 3) {

            String targetName = args[1];
            String channelName = args[2];

            Player target = Bukkit.getPlayer(targetName);

            assert target != null;

            if (target.isOnline()) {

                Channel channel = Channel.getChannel(channelName);

                assert channel != null;

                try {

                    if (channel.exists()) {

                        if (!channel.equals(PlayerChannel.getPlayerChannel(target.getUniqueId()))) {

                            try {

                                String permission = channel.getPermission();
                                if (!target.getUniqueId().equals(player.getUniqueId())) {

                                    if (target.hasPermission(permission)) {

                                        player.sendMessage(chat.replace(player, target, channel, lang.getString("Channel.Join_Player"), true));
                                        target.sendMessage(chat.replace(player, target, channel, lang.getString("Channel.Join_Target"), true));
                                        channel.switchChannel(target.getUniqueId());

                                    } else
                                        player.spigot().sendMessage(chat.hoverMessage(chat.replace(player, target, channel, lang.getString("Exceptions.Channel_No_Access"), true), permission));

                                } else {

                                    player.sendMessage(chat.replace(player, target, channel, lang.getString("Channel.Join_Target"), true));
                                    channel.switchChannel(target.getUniqueId());
                                }
                            } catch (ChannelLimitException e) {
                                player.sendMessage(chat.replace(player, target, channel, e.getMessage(), true));
                            }
                        } else
                            player.sendMessage(chat.replace(player, channel, lang.getString("Channel.Already_In_Channel_Target"), true));
                    }

                } catch (ChannelNoExistsException e) {
                    player.sendMessage(chat.replace(player, target, channel, e.getMessage(), true));
                }

            } else
                player.sendMessage(chat.replace(player, target, lang.getString("Player_No_Online"), true));

        } else
            player.sendMessage(chat.replace(player, lang.getString("Syntax_Error"), true).replaceAll("%oblivion_command_syntax%", getSyntax()));

    }

}
