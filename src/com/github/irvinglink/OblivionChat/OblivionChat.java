package com.github.irvinglink.OblivionChat;

import com.github.irvinglink.OblivionChat.Commands.ChannelCommand;
import com.github.irvinglink.OblivionChat.Handlers.Channel;
import com.github.irvinglink.OblivionChat.Handlers.Chat;
import com.github.irvinglink.OblivionChat.Listeners.AsyncPlayerChat;
import com.github.irvinglink.OblivionChat.Listeners.PlayerJoin;
import com.github.irvinglink.OblivionChat.Listeners.PlayerQuit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class OblivionChat extends JavaPlugin {

    private Chat chat;
    //private CacheMonitor cacheMonitor;
    //private CacheManager cacheManager;

    private File mainFolder = getDataFolder();

    private File configFile;
    private File channelFile;
    private File langFile;

    private FileConfiguration config;
    private FileConfiguration channel;
    private FileConfiguration lang;

    private Channel defaultChannel;

    @Override
    public void onLoad() {

        this.chat = new Chat();

        chat.registerKeys();

        setupFiles();

        Channel.loadChannels(this);

        defaultChannel = Channel.getDefaultChannel();

    }

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);

        new ChannelCommand("channel", "OblivionChat.Channel", false);

    }

    @Override
    public void onDisable() {
    }

    public void setupFiles() {

        this.configFile = new File(mainFolder, "config.yml");
        this.channelFile = new File(mainFolder, "channels.yml");
        this.langFile = new File(mainFolder, "lang.yml");

        if (!mainFolder.exists()) mainFolder.mkdirs();


        if (!configFile.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(configFile.getName())), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!channelFile.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(channelFile.getName())), channelFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!langFile.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(langFile.getName())), langFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void saveConfig() {

        if (config != null) {
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (channel != null) {
            try {
                channel.save(channelFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (lang != null) {
            try {
                lang.save(langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public FileConfiguration getConfig() {

        if (config != null) return config;

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }

    public FileConfiguration getChannel() {

        if (channel != null) return channel;

        channel = new YamlConfiguration();

        try {
            channel.load(channelFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return channel;
    }

    public FileConfiguration getLang() {

        if (lang != null) return lang;

        lang = new YamlConfiguration();

        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return lang;
    }

    /*public CacheManager getCacheManager() {
        return cacheManager;
    }

    public File getCacheFile() {
        return cacheMonitor.getCacheFile();
    }*/

    public Chat getChat() {
        return chat;
    }

    public Channel getDefaultChannel() {
        return defaultChannel;
    }

}
