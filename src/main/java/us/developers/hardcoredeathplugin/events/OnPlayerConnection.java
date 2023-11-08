package us.developers.hardcoredeathplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class OnPlayerConnection implements Listener {
    @EventHandler
    public void onConnection(PlayerLoginEvent playerLoginEvent) {
        HardcoreDeathPlugin.db.addPlayer(playerLoginEvent.getPlayer());
    }
}
