package us.developers.hardcoredeathplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;
import us.developers.hardcoredeathplugin.utils.MessagesUtils;
import us.developers.hardcoredeathplugin.utils.RespawnUtils;

public class OnPlayerDeath implements Listener {
    @EventHandler
    public void playerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        HardcoreDeathPlugin.db.addDeath(player);
        int playerDeaths = HardcoreDeathPlugin.db.getDeaths(player);

        if (playerDeaths == 1) {
            HardcoreDeathPlugin.db.setRepayMode(player, 1);
            HardcoreDeathPlugin.db.linkRandomQuests(player);
            RespawnUtils.playerRespawn(player);

            MessagesUtils.lastChanceMessage(player);
        }

        if (playerDeaths >= 2) {
            MessagesUtils.deadForeverMessage(player);
            HardcoreDeathPlugin.db.setRepayMode(player, 0);
        }
    }
}
