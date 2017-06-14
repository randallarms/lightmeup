package com.kraken.lightmeup;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LMUListener implements Listener {
	
	Main plugin;
	LightProcessing lp = new LightProcessing(plugin);
	
  //Constructor
	public LMUListener(Main plugin, LightProcessing lp ) {
		this.plugin = plugin;
		this.lp = lp;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		Location underfoot = player.getLocation().add(0, -1, 0);
		
		lp.checkBlock(player, underfoot);
		
	}
      
}
