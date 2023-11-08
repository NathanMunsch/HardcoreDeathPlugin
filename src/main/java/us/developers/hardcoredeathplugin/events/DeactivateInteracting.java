package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class DeactivateInteracting implements Listener {
    @EventHandler
    public void whenInteracting(PlayerInteractEvent playerInteractEvent) {
        if (HardcoreDeathPlugin.db.getRepayMode(playerInteractEvent.getPlayer())) {
            playerInteractEvent.setCancelled(true);
        }
    }
}
