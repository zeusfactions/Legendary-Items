package com.zeusfactions.legendaryitems.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Preconditions;
import com.zeusfactions.legendaryitems.Main;
import com.zeusfactions.legendaryitems.Items.LegendaryItem;
import com.zeusfactions.legendaryitems.Storage.LegendaryItems;
import com.zeusfactions.legendaryitems.Utils.RPGItemsUtils;

public class OwnCommand extends Subcommand
{
	
	private static final String COMMAND_STRING = "disown";
	private Main main = Main.getInstance();
	
	public static String getCommand(){
		return COMMAND_STRING;
	}
	
	@Override
	protected String getPermission()
	{
		return main.getName()+"." + getCommand();
	}
	
	@Override
	protected boolean needsPlayer(){
		return true;
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
		
		RPGItemsUtils rpgitemsUtils = RPGItemsUtils.getInstance();
		LegendaryItems legendaryItems = LegendaryItems.getInstance();
		Player player = (Player) sender;
		
		
		if(args.length == 1)
		{
			PlayerInventory playerInventory = player.getInventory();
			ItemStack item = playerInventory.getItemInMainHand();
			if(!(item == null || item.getType() == Material.AIR))
			{
				if(rpgitemsUtils.isLegendaryQuality(item))
				{
					if(legendaryItems.isRegisteredLegendary(item))
					{
						
					}
					else 
					{
						player.sendMessage("The item you are holding is not a legendary item. You must be holding a legendary item to use this command");
					}
				}
				else
				{
					player.sendMessage("The item you are holding is not a legendary item. You must be holding a legendary item to use this command");
				}
			} 
			else 
			{
				player.sendMessage("You must be holding a legendary item to use this command.");
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
