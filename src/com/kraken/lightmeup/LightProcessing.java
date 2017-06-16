package com.kraken.lightmeup;

import java.util.ArrayList;
import java.util.WeakHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LightProcessing {
	
	Main plugin;
	ArrayList<String> litPlayers = new ArrayList<String>();
	WeakHashMap<Player, Location> litBlocks = new WeakHashMap<Player, Location>();
	WeakHashMap<Location, Material> litTypes = new WeakHashMap<Location, Material>();
	WeakHashMap<Location, Byte> litData = new WeakHashMap<Location, Byte>();
	
  //Constructor
	public LightProcessing(Main plugin) {
		this.plugin = plugin;
	}
	
  //"light" command processing
	public void lightUp(Player player) {
		
		Location underfoot = player.getLocation().add(0, -1, 0);
		Material mat = underfoot.getBlock().getType();
		
		if ( !mat.equals(Material.AIR) && !mat.equals(Material.SEA_LANTERN) ) {
			
			saveBlock(player, underfoot);
			underfoot.getBlock().setType(Material.SEA_LANTERN);
			
		} else {
			saveBlock(player, underfoot);
		}
		
	}
	
	public void lightOff(Player player) {
		
		restoreBlock(player);
		litPlayers.remove(player.getUniqueId().toString());
		
	}
	
	@SuppressWarnings("deprecation")
	public void saveBlock(Player player, Location underfoot) {
		
		litBlocks.put( player, underfoot );
		litTypes.put( underfoot, underfoot.getBlock().getType() );
		litData.put( underfoot, underfoot.getBlock().getData() );
		player.sendMessage("Underfoot loc: " + underfoot.getBlock().getLocation());
		
		if ( !litPlayers.contains( player.getUniqueId().toString() ) ) {
			litPlayers.add( player.getUniqueId().toString() );
			player.sendMessage("Player saved to litPlayers as: " + player.getUniqueId().toString() );
		}
		
	}
	
	public void checkBlock (Player player, Location underfoot) {
		
		if ( litPlayers.contains( player.getUniqueId().toString() ) ) {
			
			Location savedLoc = litBlocks.get(player);
			
			player.sendMessage("Player is in litPlayers...");
			
			if ( !savedLoc.equals(underfoot) ) {
				restoreBlock(player);
				lightUp(player);
			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void restoreBlock(Player player) {
		
		Material type = litTypes.get( litBlocks.get(player) );
		byte data = litData.get( litBlocks.get(player) );
		Location savedLoc = litBlocks.get(player);
		
		savedLoc.getBlock().setType(type, true);
		player.sendMessage("Type: " + type);
		savedLoc.getBlock().setData(data);
		litTypes.remove(savedLoc);
		litData.remove(savedLoc);
		
	}
	
}
