package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnMobKilled implements Listener {
    @EventHandler
    public void playerKilledMob(EntityDeathEvent entityDeathEvent) {
        if (entityDeathEvent.getEntity().getKiller() != null) {
            Player player = entityDeathEvent.getEntity().getKiller();
            if (HardcoreDeathPlugin.db.getRepayMode(player)) {

                //Quest kill a wither
                if (entityDeathEvent.getEntity().getName().toLowerCase().contains("wither")) {
                    HardcoreDeathPlugin.db.addProgression(player, 1);
                }

                //Quest kill a zombie
                if (entityDeathEvent.getEntity().getName().toLowerCase().contains("zombie")) {
                    HardcoreDeathPlugin.db.addProgression(player, 4);
                }
            }
        }
    }
}
