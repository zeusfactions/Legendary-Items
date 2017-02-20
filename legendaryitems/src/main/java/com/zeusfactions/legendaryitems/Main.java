package com.zeusfactions.legendaryitems;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.zeusfactions.legendaryitems.Commands.CommandManager;
import com.zeusfactions.legendaryitems.Commands.OwnCommand;
import com.zeusfactions.legendaryitems.Storage.LegendaryItems;

import Exceptions.DependencyNotFoundException;


/**
 * Hello world!
 *
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
    	CommandManager.addComand(Arrays.asList(OwnCommand.getCommand()), new OwnCommand());	//li claim
    	
    	logger.info("Loading item list...");
    }
    
    @Override
    public void onDisable()
    {
    	
    }
	public void killPlugin()
	{
		
	}
    
    
    
}
