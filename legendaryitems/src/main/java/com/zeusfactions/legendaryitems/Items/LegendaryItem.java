package com.zeusfactions.legendaryitems.Items;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;
import com.zeusfactions.legendaryitems.Main;
import com.zeusfactions.legendaryitems.Storage.LegendaryItems;

public class LegendaryItem {
	
	private Main main = Main.getInstance();
	private Logger logger = main.getLogger();
	private LegendaryItems legendaryItems = LegendaryItems.getInstance();
	
	private Chest dungeonChest;
	private Inventory inventory;
	private ItemStack itemStack;
	
	public LegendaryItem(Chest dungeonChest, ItemStack itemStack)
	{
		logger.fine("Constructing a legendary item."); 
		logger.fine("Running preconditions...");
		
		Preconditions.checkNotNull(itemStack, "The Plugin passed a null LegendaryItem to the LegendaryItem constructor.");
		Preconditions.checkNotNull(dungeonChest, "The Plugin passed a null Inventory to the LegendaryItem constructor.");
		Preconditions.checkArgument(dungeonChest.getInventory().contains(itemStack), "The plugin tried to tie a legendary item to an inventory who did not own it.");
		
		logger.fine("Setting object variables to those in the constructor");
		this.itemStack = itemStack;
		this.dungeonChest = dungeonChest;
		this.inventory = dungeonChest.getInventory();
		
	}
	
	public void giveTo(Inventory taker) 
	{
		logger.fine("Legendary Item " + getName() + " is being given to another inventory."); 
		logger.finer("Running preconditions...");
		
		Preconditions.checkNotNull(taker, "The plugin tried to give a legendary item to a nonexistent inventory.");
		
		logger.finer("Giving item to player and removing it from its original inventory if it exists.");
		
		if(inventory!=null)
		{
			inventory.removeItem(itemStack);
		}
		
		inventory = taker;
		inventory.addItem(itemStack);
	}

	public InventoryHolder getInventoryHolder()
	{
		logger.fine("Retrieving holder of " + getName() + ".");
		
		return inventory.getHolder();
	}
	
	public boolean assignedToPlayer()
	{
		logger.fine("Finding if item is assigned to player.");
		return getPlayerAssignment() == null;
	}
	
	public Player getPlayerAssignment()
	{
		logger.fine("Retrieving " + getName() + "'s player assignment.");
		
		InventoryHolder inventoryHolder = getInventoryHolder();
		if(inventoryHolder instanceof Player){
			return (Player) inventoryHolder;
		}
		
		logger.fine("The legendary item does not seem to have a player assignment; returning null.");
		return null;
	}
	
	public void whenceItCame()
	{
		logger.fine("The legendary item " + getName() + " is being sent to its original dungeon spot.");
		
		
		logger.finer("Changing item location.");
		
		Inventory dungeonChestInventory = dungeonChest.getInventory();
		HashMap<Integer, ItemStack> missedItems = dungeonChestInventory.addItem(itemStack);
		
		if(!missedItems.isEmpty())
		{
			logger.warning("Legendary item " + getName() + " was unable to be transported back to its chest, because it was full.");
			logger.warning("The item will be removed and you will have to recreate it and assign it to the chest.");
			if(legendaryItems.getItemList().contains(this))
			{
				legendaryItems.removeItem(this);
			}
		} 
		else 
		{
			logger.finer("Item moved back to original dungeon chest.");
		}
		
		inventory.remove(itemStack);
		inventory = dungeonChestInventory;
		
		logger.finer("Deleted item from original storage location (probably a player inventory).");
		
	}
	
	public String getName(){
		
		logger.finest("Retrieving item name.");
		return itemStack.getItemMeta().getDisplayName();
	}
	
	public ItemStack getItemStack(){
		logger.finer("Retrieving itemStack from legendaryItem " + getName());
		return itemStack;
	}
	
	
}
