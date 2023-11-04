package us.developers.hardcoredeathplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import us.developers.hardcoredeathplugin.database.Database;
import us.developers.hardcoredeathplugin.events.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class HardcoreDeathPlugin extends JavaPlugin implements Listener {

    public static Database db;

    @Override
    public void onEnable() {
        if (getServer().isHardcore()) {
            getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true));
            System.out.println("Due to the way the plugin HardcoreDeathPlugin works, " +
                    "the gamerule doImmediateRespawn has been activated to allow the plugin to work correctly");

            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            try {
                db = new Database(getDataFolder().getAbsolutePath() + "/HardcoreDeathPlugin.db");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database : " + e.getMessage());
                Bukkit.getPluginManager().disablePlugin(this);
            }

            getServer().getPluginManager().registerEvents(new PlayerPickedUpItem(), this);
            getServer().getPluginManager().registerEvents(new PreventChestOpening(), this);
            getServer().getPluginManager().registerEvents(new RegisterPlacedBlock(), this);

            System.out.println("The HardcoreDeathPlugin has correctly started");
        }
        else {
            System.out.println("The HardcoreDeathPlugin hasn't started because the hardcore mode is not enabled");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        db.closeConnection();
    }
}
