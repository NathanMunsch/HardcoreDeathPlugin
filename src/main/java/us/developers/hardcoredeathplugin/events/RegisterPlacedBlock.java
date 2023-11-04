package us.developers.hardcoredeathplugin.events;

import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class RegisterPlacedBlock implements Listener {
    @EventHandler
    public void blockPlaced(BlockPlaceEvent blockPlaceEvent) {
        if (blockPlaceEvent.getBlock().getState() instanceof TileState) {
            TileState tileState = (TileState) blockPlaceEvent.getBlock().getState();
            PersistentDataContainer persistentDataContainer = tileState.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(
                    HardcoreDeathPlugin.getPlugin(HardcoreDeathPlugin.class),
                    "isPlacedByAPlayer"
            );
            persistentDataContainer.set(namespacedKey, PersistentDataType.BOOLEAN, true);
            tileState.update();
        }
    }
}
