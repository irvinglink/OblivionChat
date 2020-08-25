package com.github.irvinglink.OblivionChat.Commands.Builders;

import com.github.irvinglink.OblivionChat.Handlers.Chat;
import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CommandBuilder implements CommandExecutor {

    private final OblivionChat plugin = OblivionChat.getPlugin(OblivionChat.class);
    private final Chat chat = plugin.getChat();

    private final String cmdName;
    private final String permission;
    private final boolean console;

    public CommandBuilder(String cmdName, String permission, boolean console) {

        this.cmdName = cmdName;

        this.permission = permission;
        this.console = console;

        Objects.requireNonNull(plugin.getServer().getPluginCommand(cmdName)).setExecutor(this);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {

        if (!cmd.getLabel().equalsIgnoreCase(cmdName)) return true;

        if (!sender.hasPermission(permission)) {
            sender.sendMessage(chat.replace((Player) sender, plugin.getLang().getString("No_Permission"), true));
            return true;
        }

        if (!console && !(sender instanceof Player)) {
            sender.sendMessage(chat.str(Chat.EnumChat.PluginPrefix.getStr() + "&cOnly players can use this command!"));
            return true;
        }

        execute((Player) sender, args);
        return false;
    }

    public abstract void execute(Player player, String[] args);

    protected OblivionChat getPlugin() {
        return plugin;
    }

    protected Chat getChat() {
        return chat;
    }
}
