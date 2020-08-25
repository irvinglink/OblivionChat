package com.github.irvinglink.OblivionChat.Commands.ChannelSubCommands;

import com.github.irvinglink.OblivionChat.Commands.Builders.SubCommand;
import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.Handlers.Chat;
import com.github.irvinglink.OblivionChat.Handlers.PlayerChannel;
import com.github.irvinglink.OblivionChat.OblivionChat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListSubCommand extends SubCommand {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);
    private final Chat chat = plugin.getChat();

    private final FileConfiguration lang = plugin.getLang();

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return lang.getString("Help.List_Description");
    }

    @Override
    public String getSyntax() {
        return "/channel list";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 1) {

            listMessage(player);

        } else
            player.sendMessage(chat.replace(player, lang.getString("Syntax_Error"), true).replaceAll("%oblivion_command_syntax%", getSyntax()));
    }

    private void listMessage(Player player) {

        // FIRST LINE
        StringBuilder line = new StringBuilder();

        int length = 60;

        for (int i = 0; i < length; i++) line.append("-");

        player.sendMessage(plugin.getChat().str("&8&m" + line.toString()));

        // CHANNEL TITLE
        String channelTitle = lang.getString("Channel_List.Title");

        StringBuilder title = new StringBuilder();

        for (int i = 0; i < ((length - (channelTitle != null ? channelTitle.length() : 0)) / 2); i++)
            title.append(" ");

        title.append(channelTitle);

        player.sendMessage(plugin.getChat().str("&f&l" + title.toString()));

        player.sendMessage(" ");

        // AVAILABLE CHANNEL LIST

        List<Channel> channelList = Channel.getChannels();

        TextComponent availableComponent = new TextComponent(chat.str(lang.getString("Channel_List.Available")));

        List<BaseComponent> channelComponentList = new ArrayList<>();

        if (!channelList.isEmpty()) {

            for (Channel channel : channelList) {
                channelComponentList.add(chat.clickHoverMessage(channel.getChannelName() + " ", channelString(player, channel),
                        "/channel join " + player.getName() + " " + channel.getChannelName()));
            }
        } else
            channelComponentList.add(new TextComponent(chat.str("Channel_List.No_Registered_Channels")));

        availableComponent.setExtra(channelComponentList);

        player.spigot().sendMessage(availableComponent);

        player.sendMessage(" ");

        //CURRENT CHANNEL
        player.sendMessage(chat.str(lang.getString("Channel_List.Current_Channel")
                + PlayerChannel.getPlayerChannel(player.getUniqueId()).getChannelName()));

        player.sendMessage(" ");

        // FINAL LINE
        player.sendMessage(plugin.getChat().str("&8&m" + line.toString()));

        player.sendMessage(" ");
    }

    private String channelString(Player player, Channel channel) {

        if (channel != null) {

            String channelName = channel.getChannelName();
            int max = channel.getMaxMembers();

            return chat.str(lang.getString("Channel_List.HoverEvent_Channel_Message") + channelName + "\n" +

                    lang.getString("Channel_List.HoverEvent_Members_Message") + channel.getMembers().size() + " &b/ &f" + ((max == -1) ? "∞" : max) + "\n" +

                    lang.getString("Channel_List.HoverEvent_Access_Message") + (player.hasPermission(channel.getPermission()) ? "&8[&a✔&8]" : "&8[&c✖&8]") + "\n" +

                    lang.getString("Channel_List.HoverEvent_Click_Message"));

        }
        return null;
    }
}
