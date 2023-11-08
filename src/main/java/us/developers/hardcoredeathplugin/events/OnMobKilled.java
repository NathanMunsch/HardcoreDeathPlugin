package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnMobKilled implements Listener {
    @EventHandler
    public void playerKilledMob(EntityDeathEvent entityDeathEvent) {
        if (HardcoreDeathPlugin.db.getRepayMode(entityDeathEvent.getEntity().getKiller())) {
            if (entityDeathEvent.getEntity().getName().equals("Zombie")) {
                // fonction en bdd
            }
            if (entityDeathEvent.getEntity().getName().equals("Wither")) {
                // fonction en bdd
            }
        }
    }
}
