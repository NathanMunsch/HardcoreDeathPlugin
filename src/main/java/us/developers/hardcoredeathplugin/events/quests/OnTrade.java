package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.inventory.Merchant;

// Not sure that it's possible to do

public class OnTrade implements Listener {
    @EventHandler
    public void playerTradedNPC(TradeSelectEvent tradeSelectEvent)
    {
        if(tradeSelectEvent.getMerchant().isTrading())
        {
            Player player = (Player) tradeSelectEvent.getMerchant().getTrader();
        }
    }
}
