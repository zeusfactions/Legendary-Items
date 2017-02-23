package com.zeusfactions.legendaryitems.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.zeusfactions.legendaryitems.Storage.LegendaryItems;

public class OnPlayerKilled {
	
	private LegendaryItems legendaryItems = LegendaryItems.getInstance();
	
	@EventHandler
	public void onPlayerKilled(EntityDeathEvent event)
	{
		Entity entity = event.getEntity();
		
		//The entity needs to be a player,
		if(!(entity instanceof Player))
		{
			return;
		} 
		
		Player player = (Player) entity;
		Entity killerEntity = player.getKiller();
		
		//That was killed by another player,
		if(!(killerEntity instanceof Player))
		{
			return;
		}
		
		Player killer = (Player) killerEntity;
		
		if(legendaryItems.playerHasItem(player))
		{
			legendaryItems.transferItems(player.getInventory(), killer.getInventory());;
		}
	}
}
