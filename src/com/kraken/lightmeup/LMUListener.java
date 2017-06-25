package com.kraken.lightmeup;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
	public void onPlayerJoin(PlayerJoinEvent e) {
		
	    Player player = e.getPlayer();
	    String UUIDString = player.getUniqueId().toString();
	    boolean allowed = plugin.getConfig().getBoolean(UUIDString + ".allowed");
	    
	    if (allowed) {
	    	plugin.playerAllowed(UUIDString, true);
	    } else {
	    	plugin.playerAllowed(UUIDString, false);
	    }
	    
	  }
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		Location underfoot = player.getLocation().add(0, -1, 0);
		
		if (!underfoot.getBlock().getType().equals(Material.SEA_LANTERN)) {
			lp.checkBlock(player, underfoot);
		}
		
	}
      
}
