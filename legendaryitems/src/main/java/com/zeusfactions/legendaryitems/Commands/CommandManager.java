package com.zeusfactions.legendaryitems.Commands;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor
{
	private static HashMap<List<String>, Subcommand> commands = new HashMap<List<String>, Subcommand>();
	private static final String BASE_COMMAND = "li";
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length >= 1)
		{
			boolean match = false; 
			
			for(List<String> s : commands.keySet())
			{
				if(s.contains(args[0]))
				{
					commands.get(s).runCommand(sender, cmd, label, args);
					match = true;
				}
			}
			
			if(!match)
			{
				sender.sendMessage("This command does not exist. Did you mean something different?");
			}
		}
		else
		{
			Bukkit.dispatchCommand(sender, BASE_COMMAND + " help");
		}
		
		return true;
	}
	
	public static void addComand(List<String> cmds, Subcommand s)
	{
		commands.put(cmds, s);
	}
	
	public static String getBaseCommand()
	{
		return BASE_COMMAND;
	}
	
}