package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnFishCaught implements Listener {

    @EventHandler
    public void playerCatchedFish(PlayerFishEvent playerFishEvent) {
        if (HardcoreDeathPlugin.db.isInRepayMode(playerFishEvent.getPlayer())) {
            if (playerFishEvent.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                // fonction a appeler en bdd
            }
        }
    }
}
