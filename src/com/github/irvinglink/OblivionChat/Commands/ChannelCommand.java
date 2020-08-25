package com.github.irvinglink.OblivionChat.Commands;

import com.github.irvinglink.OblivionChat.Commands.Builders.CommandBuilder;
import com.github.irvinglink.OblivionChat.Commands.Builders.SubCommand;
import com.github.irvinglink.OblivionChat.Commands.ChannelSubCommands.JoinSubCommand;
import com.github.irvinglink.OblivionChat.Commands.ChannelSubCommands.ListSubCommand;
import com.github.irvinglink.OblivionChat.Commands.ChannelSubCommands.SwitchSubCommand;
import com.github.irvinglink.OblivionChat.Handlers.Channel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelCommand extends CommandBuilder implements TabCompleter {

    private final List<SubCommand> subCommandList = new ArrayList<>();

    public ChannelCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);

        subCommandList.add(new JoinSubCommand());
        subCommandList.add(new SwitchSubCommand());
        subCommandList.add(new ListSubCommand());

    }

    @Override
    public void execute(Player player, String[] args) {

        if (args.length == 0) {

            player.sendMessage("");

            player.sendMessage(getChat().str(getPlugin().getLang().getString("Plugin_Prefix") + " &fCommand List"));
            subCommandList.forEach(x -> player.sendMessage(getChat().str("&a" + x.getSyntax() + " &7- " + x.getDescription())));

            player.sendMessage("");
            return;

        }

        for (SubCommand subCommand : subCommandList) {
            if (args[0].equalsIgnoreCase(subCommand.getName())) {
                subCommand.perform(player, args);
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (args.length == 1)
            return subCommandList.stream().map(SubCommand::getName).collect(Collectors.toList());

        if (args.length == 2) {

            switch (args[0]) {

                case "switch":
                    return Channel.getChannels().stream().map(Channel::getChannelName).collect(Collectors.toList());

                case "join":
                default:
                    return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());

            }
        }

        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("join")) {
                return Channel.getChannels().stream().map(Channel::getChannelName).collect(Collectors.toList());
            }

        }
        return null;
    }
}
