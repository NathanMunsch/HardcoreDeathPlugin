package us.developers.hardcoredeathplugin.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

import java.util.ArrayList;

public class DeactivateInteracting implements Listener {
    @EventHandler
    public void whenInteracting(PlayerInteractEvent playerInteractEvent) {
        // List of blocks with deactivate interacting
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(Material.CHEST);
        materials.add(Material.BARREL);
        materials.add(Material.SHULKER_BOX);

        if (HardcoreDeathPlugin.db.getRepayMode(playerInteractEvent.getPlayer())) {
            if (playerInteractEvent.hasBlock()) {
                if (materials.contains(playerInteractEvent.getClickedBlock().getType())) {
                    playerInteractEvent.setCancelled(true);
                }
            }
        }
    }
}
