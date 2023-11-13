package us.developers.hardcoredeathplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;
import us.developers.hardcoredeathplugin.utils.MessagesUtils;

public class Abandon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (HardcoreDeathPlugin.db.getRepayMode(player)) {

                MessagesUtils.abandonMessage(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (player.isOnline()) {
                            player.setHealth(0);
                        }
                    }
                }.runTaskLater(HardcoreDeathPlugin.getPlugin(HardcoreDeathPlugin.class), 600); //600 ticks = 30 seconds
            }
        }
        return true;
    }
}
