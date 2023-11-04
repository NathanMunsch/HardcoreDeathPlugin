package us.developers.hardcoredeathplugin;

import org.bukkit.Bukkit;
import us.developers.hardcoredeathplugin.database.Database;
import us.developers.hardcoredeathplugin.events.PlayerDiedEvent;
import us.developers.hardcoredeathplugin.events.PlayerJoinedEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class HardcoreDeathPlugin extends JavaPlugin implements Listener {

    public static Database db;

    @Override
    public void onEnable() {
        if (getServer().isHardcore()) {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            try {
                db = new Database(getDataFolder().getAbsolutePath() + "/HardcoreDeathPlugin.db");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database : " + e.getMessage());
                Bukkit.getPluginManager().disablePlugin(this);
            }

            getServer().getPluginManager().registerEvents(new PlayerDiedEvent(), this);
            getServer().getPluginManager().registerEvents(new PlayerJoinedEvent(), this);

            System.out.println("The HardcoreDeathPlugin has started");
        }
        else {
            System.out.println("The HardcoreDeathPlugin hasn't started because the hardcore mode is not enabled");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
