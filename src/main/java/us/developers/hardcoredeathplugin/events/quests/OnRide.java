package us.developers.hardcoredeathplugin.events.quests;

import org.bukkit.util.Vector;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEvent;
import us.developers.hardcoredeathplugin.HardcoreDeathPlugin;


public class OnRide implements Listener {
    @EventHandler
    public void playerRidingVehicle(VehicleEvent vehicleEvent)
    {
        Player player = (Player) vehicleEvent.getVehicle().getPassengers();
        if(HardcoreDeathPlugin.db.getRepayMode(player))
        {
            if(vehicleEvent.getVehicle().getType() == EntityType.MINECART)
            {

            }
        }
    }
}
