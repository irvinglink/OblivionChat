package com.github.irvinglink.OblivionChat.Handlers;

import com.github.irvinglink.OblivionChat.OblivionChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LowerUse {

    public List<Player> getNearbyPlayers(UUID uuid, double radius) {

        List<Player> returnedList = new ArrayList<>();

        Player player = Bukkit.getPlayer(uuid);

        assert player != null;

        Location playerLocation = player.getLocation();

        Channel playerChannel = PlayerChannel.getPlayerChannel(uuid);

        for (UUID targetUniqueId : playerChannel.getMembers()) {

            Player target = Bukkit.getPlayer(targetUniqueId);
            assert target != null;

            if (target.getLocation().distance(playerLocation) < radius + 1) returnedList.add(target);

        }

        return returnedList;
    }
}
