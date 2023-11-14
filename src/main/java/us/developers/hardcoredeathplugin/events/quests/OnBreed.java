package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnBreed implements Listener {
    @EventHandler
    public void breedEvent(EntityBreedEvent entityBreedEvent) {
        if (entityBreedEvent.getEntity().getName().toLowerCase().contains("cow")) {
            Player player = (Player) entityBreedEvent.getBreeder();
            HardcoreDeathPlugin.db.addProgression(player, 9);
        }
    }
}
