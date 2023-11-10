package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnItemCrafted implements Listener {
    @EventHandler
    public void itemCrafted(CraftItemEvent craftItemEvent) {
        if (craftItemEvent.getWhoClicked() instanceof Player) {
            Player player = (Player) craftItemEvent.getWhoClicked();
            if (HardcoreDeathPlugin.db.getRepayMode(player)) {

                // Quest craft an anvil
                if (craftItemEvent.getRecipe().getResult().getType() == Material.ANVIL) {
                    HardcoreDeathPlugin.db.addProgression(player, 3);
                }

                // Quest craft a diamond pickaxe
                if (craftItemEvent.getRecipe().getResult().getType() == Material.DIAMOND_PICKAXE) {
                    HardcoreDeathPlugin.db.addProgression(player, 7);
                }

                // Quest craft a painting
                if (craftItemEvent.getRecipe().getResult().getType() == Material.PAINTING) {
                    HardcoreDeathPlugin.db.addProgression(player, 11);
                }

                // Quest craft a enchanting table
                if (craftItemEvent.getRecipe().getResult().getType() == Material.ENCHANTING_TABLE) {
                    HardcoreDeathPlugin.db.addProgression(player, 13);
                }
            }
        }
    }
}
