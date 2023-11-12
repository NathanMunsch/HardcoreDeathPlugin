package us.developers.hardcoredeathplugin.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnPlayerDeath implements Listener {
    @EventHandler
    public void playerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        HardcoreDeathPlugin.db.addDeath(player);
        int playerDeaths = HardcoreDeathPlugin.db.getDeaths(player);

        if (playerDeaths == 1) {
            HardcoreDeathPlugin.db.setRepayMode(player, 1);
            HardcoreDeathPlugin.db.linkRandomQuests(player);
            Location respawnLocation = player.getBedSpawnLocation();
            if (respawnLocation == null) {
                respawnLocation = player.getWorld().getSpawnLocation();
            }

            player.setHealth(20);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(respawnLocation);
            player.sendMessage(ChatColor.YELLOW + "You are dead but you have one last chance to regain your life, complete the quests and you will live.");
            player.sendMessage(ChatColor.GREEN + "/quests : To know which quests to do.");
            player.sendMessage(ChatColor.GREEN + "/abandon : To give up and die forever.");
        }

        if (playerDeaths >= 2) {
            player.sendMessage(ChatColor.YELLOW + "You lost your last chance to survive, you are now dead forever.");
            HardcoreDeathPlugin.db.setRepayMode(player, 0);
        }
    }
}
