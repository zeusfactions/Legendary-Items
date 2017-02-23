package com.zeusfactions.legendaryitems.Storage;

import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;
import com.zeusfactions.legendaryitems.Main;
import com.zeusfactions.legendaryitems.Items.LegendaryItem;

public class LegendaryItems {
	
	private HashSet<LegendaryItem> legendaryItems;
	private Main main;
	private Logger logger;
	
	private static LegendaryItems INSTANCE;
	private LegendaryItems()
	{
		legendaryItems = new HashSet<LegendaryItem>();
		main = Main.getInstance();
		logger = main.getLogger();
	}
	public static LegendaryItems getInstance()
	{
		if(INSTANCE == null){
			INSTANCE = new LegendaryItems();
		}
		return INSTANCE;
	}
	
	public HashSet<LegendaryItem> getItemList(){
		return legendaryItems;
	}
	
	/**
	 * This function returns <i>a</i> legendary item with the following characteristics, but so far cannot validate whether or not
	 * it's the <i>right</i> legendary item. Hopefully with some NMS this might change in the future. 
	 * 
	 * @param itemStacks (The itemStacks that the legendary item has)
	 * @param inventory (The inventory it's contained in)
	 * @return A legendary item has the itemStacks specified and is contained in the inventory specified. Returns null if there is no matching legendary item. 
	 * 
	 * <br><br>
	 * 
	 * 
	 */
	public LegendaryItem getEquivalentLegendary(ItemStack[] itemStacks, Inventory inventory)
	{
		logger.finest("Checking if an item inventoryHolder has a matching legendary.");
		
		for(LegendaryItem i: legendaryItems){
			if(i.getItemStacks() == itemStacks && i.getInventory() == inventory)
			{
				logger.finest("Item found.");
				return i;
			}
		}
		logger.finest("Item not found.");
		return null;
	}
	
	/**
	 * 
	 * @param player
	 * @param item
	 * 
	 * Registers an item as legendary and includes it in the official list of legendary items.
	 */
	public void addItem(LegendaryItem item)
	{
		logger.fine("Adding LegendaryItem " + item.getName() + " to the legendary items list.");
		logger.fine("Checking preconditions...");
		Preconditions.checkNotNull(item, "The plugin passed a null Legendary Item to the registerItem method. ");
		
		legendaryItems.add(item);
	}
	
	
	public void transferItems(Inventory giver, Inventory taker)
	{
		logger.fine("Transfering legendary items located in one inventory to another.");
		logger.fine("Checking preconditions...");
		
		Preconditions.checkNotNull(giver, "The giving inventory passed into transferItems(giver, taker) was null.");
		Preconditions.checkNotNull(taker, "The receiving inventory passed into transferItems(giver, taker) was null.");
		
		for(LegendaryItem i: legendaryItems){
			if(i.getInventoryHolder()==giver){
				i.giveTo(taker);
				logger.fine("Item " + i.getName() + " given to taker.");
			}
		}
	}
	
	public void transferItem(LegendaryItem legendaryItem, Inventory giver, Inventory taker)
	{
		logger.fine("Transfering legendary items located in one inventory to another.");
		logger.fine("Checking preconditions...");
		
		Preconditions.checkNotNull(legendaryItem, "The legendary item to trasnfer was null.");
		Preconditions.checkNotNull(giver, "The giving inventory passed into transferItems(giver, taker) was null.");
		Preconditions.checkNotNull(taker, "The receiving inventory passed into transferItems(giver, taker) was null.");
		
		for(LegendaryItem i: legendaryItems){
			if(i.getInventoryHolder()==giver){
				i.giveTo(taker);
				logger.fine("Item transfered from giver to taker.");
				return;
			}
		}
		
		//Doesn't get to this point unless no item was found.
		logger.fine("The plugin tried to transfer a legendary item but found out that that legendary item doesn't exist. Skipping transfer...");
	}
	
	public boolean playerHasItem(Player player)
	{
		logger.fine("Checking if a player has a legendary item.");
		logger.fine("Checking preconditions...");
		
		Preconditions.checkNotNull(player, "The plugin passed a null player object to the LegendaryItems hasItem() method.");
		
		for(LegendaryItem i: legendaryItems){
			if(i.getInventoryHolder() == player){
				return true;
			}
		}
		return false;
	}
	
	public void modifyLegendaryItem(LegendaryItem legendaryItemBefore, LegendaryItem legendaryItemAfter)
	{
		logger.fine("Retrieving legendary item from comprehensive list.");
		logger.fine("Checking preconditions...");
		
		Preconditions.checkNotNull(legendaryItemBefore, "The passed legendaryItem the plugin was attempting to modify was null. We don't store null legendary items.");
		Preconditions.checkNotNull(legendaryItemAfter, "The plugin tried to turn a legendary item into null. This is not the correct way to remove a legendary item.");

		logger.fine("Checking if item exists in legendary item list...");
		if(legendaryItems.contains(legendaryItemBefore))
		{
			logger.fine("Retrieving and modifying legendary item.");
			
			for(LegendaryItem i: legendaryItems){
				if(i==legendaryItemBefore)
				{
					legendaryItems.remove(i);
					legendaryItems.add(legendaryItemAfter);
					
					logger.fine("Old legendary item removed and new one added.");
					return;
				}
			}
		}
		
		logger.fine("A legendary item was being modified by plugin until it found out it didn't exist. Skipping modifications...");
	}
	
	public void removeItem(LegendaryItem legendaryItem)
	{
		logger.fine("Removing item from legendaryItems list.");
		
		if(legendaryItems.contains(legendaryItem)){
			legendaryItems.remove(legendaryItem);
		}
		
		logger.fine("The plugin tried to remove a legendary item that didn't exist. Skipping removal...");
	}
	
}
