package us.developers.hardcoredeathplugin.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RespawnUtils {
    public static void playerRespawn(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.teleport(getPlayerSpawnPoint(player));
        player.getInventory().clear();
    }

    private static Location getPlayerSpawnPoint(Player player) {
        Location location = player.getWorld().getSpawnLocation();
        if (player.getBedSpawnLocation() != null) {
            location = player.getBedSpawnLocation();
        }
        return location;
    }
}
