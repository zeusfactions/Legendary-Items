package com.zeusfactions.legendaryitems.Commands;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zeusfactions.legendaryitems.Main;

public abstract class Subcommand
{
	protected Logger logger = Main.getInstance().getLogger();
	
	protected abstract String getPermission();
	
	protected boolean needsPlayer()
	{
		return false;
	}
	
	public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);

	public boolean runCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender.hasPermission(getPermission()))
		{
			if(needsPlayer())
			{
				if(sender instanceof Player)
				{
					onCommand(sender, cmd, label, args);
				} 
				else
				{
					sender.sendMessage("You must be a player to perform this command.");
				}
			}
			else
			{
				onCommand(sender, cmd, label, args);
			}
		}
		else
		{	
			sender.sendMessage("You do not have permission to perform this command!");
		}
		return true;
	}
	
}