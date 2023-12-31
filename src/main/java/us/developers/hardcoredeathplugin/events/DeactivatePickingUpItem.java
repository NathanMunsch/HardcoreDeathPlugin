package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class DeactivatePickingUpItem implements Listener {
    @EventHandler
    public void playerPickedUpItem(PlayerPickupItemEvent playerPickupItemEvent) {
        if (HardcoreDeathPlugin.db.getRepayMode(playerPickupItemEvent.getPlayer())) {
            playerPickupItemEvent.setCancelled(true);
        }
    }
}