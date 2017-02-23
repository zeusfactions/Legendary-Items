package com.zeusfactions.legendaryitems.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.base.Preconditions;
import com.zeusfactions.legendaryitems.Main;

/**
 * This CommandManager class is what gets run each time a command is executed.
 * <br>
 * Each time a command is prefaced with /li this commandmanager is called to execute it.
 * <br>
 * It then finds the correct command to run.
 */
public class CommandManager implements CommandExecutor
{
	private static HashMap<List<String>, Subcommand> commands = new HashMap<List<String>, Subcommand>();
	private static final String BASE_COMMAND = "li";
	private Logger logger = Main.getInstance().getLogger();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		logger.fine("Command entered. Checking preconditions.");
		Preconditions.checkNotNull(sender, "A command got sent but the sender was 'null'.");
		Preconditions.checkNotNull(label, "A command got sent but the label was 'null'");
		
		if(args.length >= 1)
		{
			//The player has typed /li {COMMAND}
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
				sender.sendMessage("This command does not exist. Type /li help for help.");
			}
		}
		else
		{
			//The player just typed /li.
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