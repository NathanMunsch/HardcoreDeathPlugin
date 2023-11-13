package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnFishCaught implements Listener {

    @EventHandler
    public void playerCaughtFish(PlayerFishEvent playerFishEvent) {
        if (HardcoreDeathPlugin.db.getRepayMode(playerFishEvent.getPlayer())) {

            // Quest catch 5 fishes
            if (playerFishEvent.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                HardcoreDeathPlugin.db.addProgression(playerFishEvent.getPlayer(), 3);
            }
        }
    }
}
