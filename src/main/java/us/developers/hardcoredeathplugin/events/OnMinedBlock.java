package us.developers.hardcoredeathplugin.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnMinedBlock implements Listener {
    @EventHandler
    public void playerMinedBlock(BlockBreakEvent blockBreakEvent) {
        if (HardcoreDeathPlugin.db.isInRepayMode(blockBreakEvent.getPlayer())) {
            if (blockBreakEvent.getBlock().getType() == Material.ANCIENT_DEBRIS) {
                // fonction a appeler en bdd
            }
        }
    }
}
