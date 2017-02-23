package com.zeusfactions.legendaryitems.Items;

import java.lang.reflect.Array;
import java.util.Arrays;
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
	private ItemStack[] itemStacks;
	
	public LegendaryItem(Chest dungeonChest, ItemStack[] itemStacks)
	{
		logger.fine("Constructing a legendary item."); 
		logger.fine("Running preconditions...");
		
		Preconditions.checkNotNull(itemStacks, "The Plugin passed a null LegendaryItem to the LegendaryItem constructor.");
		Preconditions.checkNotNull(dungeonChest, "The Plugin passed a null Inventory to the LegendaryItem constructor.");
		for(ItemStack i : itemStacks)
		{
			Preconditions.checkArgument(dungeonChest.getInventory().contains(i), "The plugin tried to make a legendary item"
					+ " but its item set wasn't in the associated chest.");
		}
		
		logger.fine("Setting object variables to those in the constructor");
		
		this.itemStacks = itemStacks;
		this.dungeonChest = dungeonChest;
		this.inventory = dungeonChest.getInventory();
		
	}
	
	public void giveTo(Inventory taker) 
	{
		validate();
		
		logger.fine("Legendary Item " + getName() + " is being given to another inventory."); 
		logger.finer("Running preconditions...");
		
		Preconditions.checkNotNull(taker, "The plugin tried to give a legendary item to a nonexistent inventory.");
		
		logger.finer("Giving item to player and removing it from its original inventory if it exists.");
		
		if(inventory!=null)
		{
			HashMap<Integer, ItemStack> unremovedItems = inventory.removeItem(itemStacks);
			if(!unremovedItems.isEmpty())
			{
				logger.warning("The plugin attempted to give someone's legendary item set to someone else but was unable to "
						+ "remove the entire legendary item set from the original player.");
			}
		}
		
		inventory = taker;
		inventory.addItem(itemStacks);
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
	
	public void moveToDungeonChest()
	{
		logger.fine("The legendary item " + getName() + " is being sent to its original dungeon spot.");
		
		logger.finer("Changing item location.");
		giveTo(dungeonChest.getInventory());
		
		logger.finer("Deleted item from original storage location (probably a player inventory).");
		
	}
	
	public String getName(){
		logger.finest("Retrieving first item name.");
		return itemStacks[0].getItemMeta().getDisplayName();
	}
	
	public boolean sameItemStacks(ItemStack[] itemStacks){
		return this.itemStacks == itemStacks;
	}

	public Inventory getInventory()
	{
		logger.finer("Retrieving inventory from legendaryItem " + getName());
		return inventory;
	}

	
	/**
	 * This function makes sure all variables are correct, if they are not correct attempts to update them,
	 * and then if it can't deletes the LegendaryItem.
	 * 
	 * This should be called at the beginning of each inventory-related function in this class.
	 */
	private void validate()
	{
		Inventory correctInventory = inventory.getHolder().getInventory();
		if(inventory != correctInventory)
		{
			for(ItemStack i: itemStacks)
			{	
				if(correctInventory.contains(i))
				{			
					inventory = correctInventory;
				} 
				else 
				{
					logger.warning("A legendary item has been lost");
					logger.warning("Removing this legendary item from the legendary items list because it's fucking gone.");
					deleteItem();
					return;
				}
			}
		}
		
	}

	private void deleteItem()
	{
		inventory.removeItem(itemStacks);
		inventory.getHolder().getInventory().removeItem(itemStacks);
		legendaryItems.removeItem(this);
	}

	public ItemStack[] getItemStacks()
	{
		return itemStacks;
	}

	public boolean containsItemStacks(ItemStack[] items)
	{
		return Arrays.asList(itemStacks).containsAll(Arrays.asList(items));
	}
	
	
}
