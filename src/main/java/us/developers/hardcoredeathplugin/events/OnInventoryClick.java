package us.developers.hardcoredeathplugin.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;
import us.developers.hardcoredeathplugin.commands.Quests;
import us.developers.hardcoredeathplugin.utils.MessagesUtils;
import us.developers.hardcoredeathplugin.utils.RespawnUtils;

public class OnInventoryClick implements Listener {
    @EventHandler
    public void inventoryClicked(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().equals(Quests.inventory)) {
            if (inventoryClickEvent.getCurrentItem() != null) {
                if (inventoryClickEvent.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                    Player player = (Player) inventoryClickEvent.getWhoClicked();
                    boolean questsSucceeded = true;
                    for (int quest : HardcoreDeathPlugin.db.getPlayerQuests(player)) {
                        int questObjective = HardcoreDeathPlugin.db.getQuestObjective(quest);
                        int playerProgression = HardcoreDeathPlugin.db.getProgression(player, quest);
                        if (playerProgression < questObjective) {
                            questsSucceeded = false;
                        }
                    }
                    if (questsSucceeded) {
                        HardcoreDeathPlugin.db.setRepayMode(player, 0);
                        RespawnUtils.playerRespawn(player);
                        MessagesUtils.anotherLifeMessage(player);
                    }
                }
            }
            inventoryClickEvent.setCancelled(true);
        }
    }
}
