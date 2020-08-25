package com.github.irvinglink.OblivionChat.Handlers;

import com.github.irvinglink.OblivionChat.Exceptions.ChannelLimitException;
import com.github.irvinglink.OblivionChat.Exceptions.ChannelNoExistsException;
import com.github.irvinglink.OblivionChat.OblivionChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Channel {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);
    private final Chat chat;

    private final List<UUID> membersList;

    private final FileConfiguration channelFile;
    private final FileConfiguration lang = plugin.getLang();
    private final String channelName;
    private final String path;

    private static final List<Channel> channels = new ArrayList<>();
    private static Channel defaultChannel;

    public Channel(String channelName) {

        this.chat = plugin.getChat();

        this.channelFile = plugin.getChannel();
        this.channelName = channelName;

        path = "Channels." + channelName + ".";

        this.membersList = new ArrayList<>();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return channelName.equals(channel.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelName);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "plugin=" + plugin +
                ", chat=" + chat +
                ", membersList=" + membersList +
                ", channelFile=" + channelFile +
                ", channelName='" + channelName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getChannelName() {
        return channelName;
    }

    public boolean exists() throws ChannelNoExistsException {

        if (!channelFile.contains(path))
            throw new ChannelNoExistsException(lang.getString("Exceptions.Channel_No_Exists"));

        return channelFile.contains(path);
    }

    public int getMaxMembers() {
        return channelFile.getInt(path + "max_players");
    }

    public boolean isDefault() {
        return channelFile.getBoolean(path + "default");
    }

    public String getPermission() {
        return channelFile.getString(path + "permission");
    }

    public String getFormat() {
        return channelFile.getString(path + "format");
    }

    public double getRange() {
        return channelFile.getDouble(path + "range");
    }

    public void addMember(UUID uuid) throws ChannelLimitException {

        int i = getMaxMembers();

        int max = (i != -1) ? i : 1000000;

        if (max > membersList.size()) {

            if (!containsMember(uuid)) {

                membersList.add(uuid);
                PlayerChannel.add(uuid, this);

            }

        } else throw new ChannelLimitException(lang.getString("Exceptions.Channel_Reach_Max_Members"));

    }

    public void removeMember(UUID uuid) {
        if (containsMember(uuid)) {
            membersList.remove(uuid);
            PlayerChannel.remove(uuid);
        }
    }

    public void switchChannel(UUID uuid) throws ChannelLimitException {

        Channel from = PlayerChannel.getPlayerChannel(uuid);
        Channel to = this;

        from.removeMember(uuid);
        to.addMember(uuid);

    }

    public boolean containsMember(UUID uuid) {
        return membersList.contains(uuid);
    }

    public List<UUID> getMembers() {
        return membersList;
    }

    public void sendMessage(UUID uuid, String message) {

        Player player = Bukkit.getPlayer(uuid);

        if (player == null) return;

        if (getRange() != -1) {

            LowerUse lowerUse = new LowerUse();

            List<Player> targetList = lowerUse.getNearbyPlayers(uuid, getRange());

            for (Player target : targetList) {

                target.sendMessage(chat.replace(player, target, getFormat(), true)
                        .replace("%oblivion_player_message%", (player.hasPermission("OblivionChat.Color") ? chat.str(message) : message)));

            }

        } else {

            getMembers().forEach(x -> {

                Player target = Bukkit.getPlayer(x);

                assert target != null;
                target.sendMessage(chat.replace(player, target, getFormat(), true)
                        .replace("%oblivion_player_message%", (player.hasPermission("OblivionChat.Color") ? chat.str(message) : message)));

            });
        }

        if (isConsoleLog()) {
            chat.sendConsoleLog(ChatColor.stripColor(chat.replace(player, getFormat(), true)
                    .replace("%oblivion_player_message%", message)));
        }

    }

    public boolean isConsoleLog() {
        return channelFile.getBoolean(path + "console_log");
    }

    public static List<Channel> getChannels() {
        return channels;
    }

    public static Channel getChannel(String channelName) {

        Channel channel = new Channel(channelName);

        List<Channel> channelList = getChannels();

        if (channelList.contains(channel)) return channelList.get(channelList.indexOf(channel));

        return null;
    }

    public static Channel getDefaultChannel() {
        return defaultChannel;
    }

    public static void loadChannels(OblivionChat plugin) {

        FileConfiguration channel = plugin.getChannel();

        assert channel != null;

        for (String channelName : Objects.requireNonNull(channel.getConfigurationSection("Channels")).getKeys(false)) {

            if (channelName != null) {
                Channel channelToAdd = new Channel(channelName);

                channels.add(channelToAdd);

                if (channelToAdd.isDefault()) defaultChannel = channelToAdd;

            }
        }

    }

}
