package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;
import org.bukkit.Material;

public class OnMelt implements Listener {
    @EventHandler
    public void playerMeltOre(FurnaceExtractEvent furnaceExtractEvent)
    {
        Player player = furnaceExtractEvent.getPlayer();
        if (HardcoreDeathPlugin.db.getRepayMode(player))
        {
            if(furnaceExtractEvent.getItemType() == Material.GOLD_INGOT)
            {
                for (int i = 0; i < furnaceExtractEvent.getItemAmount(); i++)
                {
                    HardcoreDeathPlugin.db.addProgression(player, 10);
                }
            }
        }
    }
}
