package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;
import us.developers.hardcoredeathplugin.utils.Utils;

public class PreventChestOpening implements Listener {
    @EventHandler
    public void onChestOpening(PlayerInteractEvent playerInteractEvent) {
        if (HardcoreDeathPlugin.db.isInRepayMode(playerInteractEvent.getPlayer())) {
            if (playerInteractEvent.getClickedBlock() != null) {
                if (Utils.isBlockPlacedByAPlayer(playerInteractEvent.getClickedBlock())) {
                    playerInteractEvent.setCancelled(true);
                }
            }
        }
    }
}
