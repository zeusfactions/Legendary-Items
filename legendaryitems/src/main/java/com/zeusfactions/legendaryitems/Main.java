package com.zeusfactions.legendaryitems;

import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.zeusfactions.legendaryitems.Commands.CommandManager;
import com.zeusfactions.legendaryitems.Commands.DisownCommand;


/**
 * This is the main class. In addition to being where the plugin enables and disables, it also extends JavaPlugin, 
 * and we can use its singleton to call important methods like getLogger(). 
 */
public class Main extends JavaPlugin
{
	private static Main INSTANCE;
	private Main(){}
	public static Main getInstance()
	{
		if(INSTANCE == null){
			INSTANCE = new Main();
		}
		return INSTANCE;
	}

	private Logger logger;
	
    @Override
    public void onEnable()
    {
    	logger = getLogger();
    	logger.info("Legendary Items is enabling.");
    	
    	getCommand(CommandManager.getBaseCommand()).setExecutor(new CommandManager()); //li
    	CommandManager.addComand(Arrays.asList(DisownCommand.getCommand()), new DisownCommand());	//li claim
    	
    	logger.info("Loading item list...");
    }
    
    @Override
    public void onDisable()
    {
    	
    }
    
    
    
}
