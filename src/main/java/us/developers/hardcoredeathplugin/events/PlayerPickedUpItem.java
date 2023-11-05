package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class PlayerPickedUpItem implements Listener {
    @EventHandler
    public void playerPickedUpItem(PlayerPickupItemEvent playerPickupItemEvent) {
        if (HardcoreDeathPlugin.db.isInRepayMode(playerPickupItemEvent.getPlayer())) {
            playerPickupItemEvent.setCancelled(true);
        }
    }
}