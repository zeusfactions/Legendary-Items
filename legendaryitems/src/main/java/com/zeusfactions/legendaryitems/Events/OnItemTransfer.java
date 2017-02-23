package com.zeusfactions.legendaryitems.Events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;
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
		
		//This should NEVER be null.
		Preconditions.checkNotNull(event, "An inventoryMoveItemEvent was called but the event object was null.");
		
		ItemStack item = event.getItem();
		Inventory sourceInventory = event.getSource();
		
		//These should NEVER be null.
		Preconditions.checkNotNull(item, "An inventoryMoveItemEvent was called but the event's item object was null.");
		Preconditions.checkNotNull(sourceInventory, "An inventoryMoveItemEvent was called but the event's source inventory object was null.");
		
		
		LegendaryItem legendaryItem = legendaryItems.getEquivalentLegendary(new ItemStack[] {item}, sourceInventory);
		
		if(event.getSource() != event.getDestination() && legendaryItem != null)
		{
			
			logger.fine("Legendary Item " + item.getItemMeta().getDisplayName() + "was attempted to be moved from one inventory to another.");
			InventoryHolder sourceInventoryHolder = sourceInventory.getHolder();
			
			event.setCancelled(true); //Either way, we will cancel the event and either give the player the item ourselves
			//or not move the item. Plus, we do not want any other plugins messing with this item in this event.
			
			
			if(sourceInventoryHolder instanceof Player)
			{
				logger.fine("The player tried to illegally move it from his/her inventory to a container or some other inventory.");
				Player player = (Player) sourceInventoryHolder;
				player.sendMessage(ChatColor.RED + "You cannot move the legendary item from your inventory. It is bound to you.");
				player.sendMessage(ChatColor.RED + "The only way to get rid of it is to disown it using /li disown, or to die.");
			} 
			else 
			{
				legendaryItem.giveTo(event.getDestination());
			}
		}
	}
	
}
