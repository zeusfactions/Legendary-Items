package com.zeusfactions.legendaryitems.Commands;

import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;
import com.zeusfactions.legendaryitems.Main;
import com.zeusfactions.legendaryitems.Items.LegendaryItem;
import com.zeusfactions.legendaryitems.Storage.LegendaryItems;

/**
 * This is the class that holds the /li disown command. /li disown "de-binds" the held legendary item from the player
 * and sends it back to its assigned dungeon.
 */
public class DisownCommand extends Subcommand
{
	
	private static final String COMMAND_STRING = "disown";
	
	private Main main = Main.getInstance();
	
	public static String getCommand(){
		return COMMAND_STRING;
	}
	
	@Override
	protected String getPermission()
	{
		return main.getName() + "." + getCommand();
	}
	
	@Override
	protected boolean needsPlayer(){
		return true;
	}
	
	private LegendaryItems legendaryItems = LegendaryItems.getInstance();
	
	/**
	 * @param The player to get a legendary item from.
	 * @return The legendary item that the user is holding (or set they're holding a piece of) or null, 
	 * if they are not holding a legendary item.
	 */
	private LegendaryItem getHeldLegendary(Player player){
		
		//Null players cannot hold items.
		if(player == null)
			return null;
		
		ItemStack item = player.getInventory().getItemInMainHand(); 
		HashSet<LegendaryItem> legendaryItemsList = legendaryItems.getItemList(); 
		Iterator<LegendaryItem> iterator = legendaryItemsList.iterator();
		
		while(iterator.hasNext())
		{
			LegendaryItem legendaryItem = iterator.next();
			if(legendaryItem.getPlayerAssignment().equals(player))
			{
				ItemStack[] legendaryItemStacks = legendaryItem.getItemStacks();
				boolean inventoryContainsAllItems = true;
				boolean holdingOneOfLegendary = false;
				for(ItemStack i: legendaryItemStacks)
				{
					if(i==item)
					{
						holdingOneOfLegendary = true;
					}
					
					if(!player.getInventory().contains(i))
					{
						inventoryContainsAllItems = false;
					}
				}
				if(inventoryContainsAllItems && holdingOneOfLegendary)
				{
					return legendaryItem; //This is the whole of the legendary item the player is holding.
				}
			}
		}
		
		//The player is not holding a legendary item.
		return null;
	}
	
	@Override
	public void onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String enteredCommand = "/" + CommandManager.getBaseCommand() + getCommand();
		
		logger.fine("Command executor for " + enteredCommand + " was called.");
		logger.fine("Checking Preconditions...");
		
		Preconditions.checkArgument(args[0].equals(getCommand()), "The command executor for " + enteredCommand + " got executed in the code even though that's not the command the user entered.");
		Preconditions.checkArgument(sender instanceof Player, "Somehow a Console user tried to use " + enteredCommand + " and it got way too far in the program.");
		
		
		logger.fine("Doing regular command misuse checks.");
		Player player = (Player) sender;
		
		
		if(args.length == 1)
		{
			LegendaryItem legendaryItem = getHeldLegendary(player);
			if(legendaryItem == null)
			{
				player.sendMessage("You are not holding an item. You must be holding a legendary item to use this command.");
			} else {
				legendaryItem.moveToDungeonChest();
				player.sendMessage("Item " + legendaryItem.getName() + " disowned.");
			}
		} 
		else 
		{
			//There can only be more than one arg in args[]. Otherwise they would see a help message.
			player.sendMessage("You wrote too many arguments.");
			player.sendMessage("The correct command usage is: " + enteredCommand);
		}
		
	}

}
