package com.zeusfactions.legendaryitems.Utils;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.zeusfactions.legendaryitems.Main;

import think.rpgitems.item.RPGItem;

public class RPGItemsUtils
{
	
	private Main main = Main.getInstance();
	private Logger logger = main.getLogger();
	private Plugin rpgitems;
	private YamlConfiguration rpgitemsItemsConfig;
	
	private static RPGItemsUtils INSTANCE;
	private RPGItemsUtils()
	{
		rpgitems = main.getServer().getPluginManager().getPlugin("RPGItems");
		
		rpgitemsItemsConfig = YamlConfiguration.loadConfiguration(new File(rpgitems.getDataFolder(), "items.yml"));
	}
	public static RPGItemsUtils getInstance()
	{
		if(INSTANCE == null){
			INSTANCE = new RPGItemsUtils();
		}
		return INSTANCE;
	}
	
	public boolean isRPGItem(ItemStack item)
	{
		//Returns null if rpgitem doesn't exist
		ConfigurationSection rpgitemConfSec = getMatchingRPGItem(item);
		return rpgitemConfSec!=null;
		
	}
	
	public boolean isLegendaryQuality(ItemStack item)
	{
		ConfigurationSection rpgitemConfSec = getMatchingRPGItem(item);
		return (rpgitemConfSec!=null && rpgitemConfSec.getString("Quality").equals("LEGENDARY"));
	}
	
	/**
	 * 
	 * @param item (ItemStack that is an instance of the legendary item.
	 * @return Null if no such match item exists, the related ConfigurationSection if it does.
	 * 
	 */
	public ConfigurationSection getMatchingRPGItem(ItemStack item){
		//"Nothing" can't be an rpg item.
		if(item!=null && item.getType()!=Material.AIR)
		{
			//Checks all rpgitems and sees if any match
			ConfigurationSection itemSection = rpgitemsItemsConfig.getConfigurationSection("items");
			for(String key: itemSection.getKeys(false))
			{
				ConfigurationSection rpgitem = itemSection.getConfigurationSection(key);
				if(isMatchingRPGItem(item,rpgitem))
				{
					return rpgitem;
				}
			}
		}
		return null;
	}
	
	private boolean isMatchingRPGItem(ItemStack item, ConfigurationSection conf)
	{
		RPGItem rpgitem = new RPGItem(conf);
		return (rpgitem.toItemStack() == item);
	}
}
