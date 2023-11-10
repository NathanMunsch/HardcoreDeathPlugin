package us.developers.hardcoredeathplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

public class Abandon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (HardcoreDeathPlugin.db.getRepayMode(player)) {
                player.sendMessage(ChatColor.YELLOW + "You will give up and lose your last chance to live.");
                player.sendMessage(ChatColor.RED + "WARNING : To cancel this action, disconnect within 30 seconds for at least 1 minute.");

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
