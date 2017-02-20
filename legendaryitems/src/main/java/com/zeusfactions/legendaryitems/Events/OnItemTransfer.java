package com.zeusfactions.legendaryitems.Events;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.zeusfactions.legendaryitems.Main;
import com.zeusfactions.legendaryitems.Items.LegendaryItem;
import com.zeusfactions.legendaryitems.Storage.LegendaryItems;

import net.md_5.bungee.api.ChatColor;

public class OnItemTransfer
{
	private Main main = Main.getInstance();
	private Logger logger = main.getLogger();
	
	private LegendaryItems legendaryItems = LegendaryItems.getInstance();
	
	@EventHandler
	public void onItemMove(InventoryMoveItemEvent event){
		
		ItemStack item = event.getItem();
		Inventory sourceInventory = event.getSource();
		
		if(event.getSource() != event.getDestination() && legendaryItems.isRegisteredLegendary(item))
		{
			logger.fine("Legendary Item " + item.getItemMeta().getDisplayName() + "was attempted to be moved from one inventory to another.");
			
			InventoryHolder itemHolder = event.getSource().getHolder();
			if(itemHolder instanceof Player)
			{
				logger.fine("The player tried to illegally move it from his/her inventory to a container or some other inventory.");
				Player player = (Player) itemHolder;
				player.sendMessage(ChatColor.RED + "You cannot move the legendary item from your inventory. It is bound to you.");
				player.sendMessage(ChatColor.RED + "The only way to get rid of it is to disown it using /li disown, or to die.");
			} 
			else 
			{
				LegendaryItem updatedLegendaryItem = getLegendaryItem(item);
				updatedLegendaryItem.giveTo(event.getDestination());
				
				legendaryItems.modifyLegendaryItem(supposedLegendaryItem, updatedLegendaryItem);
			}
		}
	}
	
}
