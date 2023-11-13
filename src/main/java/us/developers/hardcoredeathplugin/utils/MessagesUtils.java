package us.developers.hardcoredeathplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessagesUtils {
    public static void lastChanceMessage(Player player) {
        player.sendMessage(ChatColor.YELLOW + "You are dead but you have one last chance to regain your life, complete the quests and you will live.");
        player.sendMessage(ChatColor.GREEN + "/quests : To know which quests to do.");
        player.sendMessage(ChatColor.GREEN + "/abandon : To give up and die forever.");
    }

    public static void deadForeverMessage(Player player) {
        player.sendMessage(ChatColor.YELLOW + "You lost your last chance to survive, you are now dead forever.");
    }

    public static void anotherLifeMessage(Player player) {
        player.sendMessage(ChatColor.YELLOW + "Congratulation! You achieved all the quests, you have now another chance to live.");
    }

    public static void abandonMessage(Player player) {
        player.sendMessage(ChatColor.YELLOW + "You will give up and lose your last chance to live.");
        player.sendMessage(ChatColor.RED + "To cancel this action, disconnect within 30 seconds for at least 1 minute.");
    }
}
