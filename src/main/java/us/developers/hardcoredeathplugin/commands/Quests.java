package us.developers.hardcoredeathplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;

import java.util.ArrayList;

public class Quests implements CommandExecutor {
    public static Inventory inventory;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();

            if (HardcoreDeathPlugin.db.getRepayMode(player)) {
                inventory = Bukkit.createInventory(player, 9, "Quests");
                ArrayList<ItemStack> itemStacks = new ArrayList<>();
                for (int quest : HardcoreDeathPlugin.db.getPlayerQuests(player)) {
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(HardcoreDeathPlugin.db.getQuestName(quest));
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(HardcoreDeathPlugin.db.getProgression(player, quest) + " / " + HardcoreDeathPlugin.db.getQuestObjective(quest));
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    itemStacks.add(itemStack);
                }

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("Finish quests");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("Click this to regain your life");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                itemStacks.add(itemStack);

                inventory.setContents(itemStacks.toArray(new ItemStack[0]));
                player.openInventory(inventory);
            }
        }
        return true;
    }
}
