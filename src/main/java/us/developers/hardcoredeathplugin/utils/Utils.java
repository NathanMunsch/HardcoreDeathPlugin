package us.developers.hardcoredeathplugin.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class Utils {
    public static boolean isBlockPlacedByAPlayer(Block block) {
        if (block.getState() instanceof TileState) {
            TileState tileState = (TileState) block.getState();
            PersistentDataContainer persistentDataContainer = tileState.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(
                    HardcoreDeathPlugin.getPlugin(HardcoreDeathPlugin.class),
                    "isPlacedByAPlayer"
            );
            return persistentDataContainer.has(namespacedKey, PersistentDataType.BOOLEAN);
        }
        return false;
    }
}
