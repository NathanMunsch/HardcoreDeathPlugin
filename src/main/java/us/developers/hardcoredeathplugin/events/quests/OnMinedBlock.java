package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnMinedBlock implements Listener {
    @EventHandler
    public void playerMinedBlock(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        if (HardcoreDeathPlugin.db.getRepayMode(player)) {

            // Quest mine an ancien debris
            if (blockBreakEvent.getBlock().getType() == Material.ANCIENT_DEBRIS) {
                HardcoreDeathPlugin.db.addProgression(player, 14);
            }
        }
    }
}
