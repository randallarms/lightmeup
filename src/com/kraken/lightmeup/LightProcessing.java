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
	
	ArrayList<Material> noGoMats = new ArrayList<Material>();
	
  //Constructor
	public LightProcessing(Main plugin) {
		this.plugin = plugin;
		noGoMats.add(Material.AIR);
		noGoMats.add(Material.SEA_LANTERN);
		noGoMats.add(Material.CHEST);
		noGoMats.add(Material.TRAPPED_CHEST);
		noGoMats.add(Material.SIGN);
		noGoMats.add(Material.WALL_SIGN);
		noGoMats.add(Material.BEACON);
		noGoMats.add(Material.ENCHANTMENT_TABLE);
		noGoMats.add(Material.WORKBENCH);
		noGoMats.add(Material.JUKEBOX);
		noGoMats.add(Material.NOTE_BLOCK);
		noGoMats.add(Material.ANVIL);
		noGoMats.add(Material.ARMOR_STAND);
		noGoMats.add(Material.BANNER);
		noGoMats.add(Material.BARRIER);
		noGoMats.add(Material.BEDROCK);
		noGoMats.add(Material.BED_BLOCK);
		noGoMats.add(Material.BED);
		noGoMats.add(Material.BREWING_STAND);
		noGoMats.add(Material.BURNING_FURNACE);
		noGoMats.add(Material.CAKE_BLOCK);
		noGoMats.add(Material.COMMAND);
		noGoMats.add(Material.COMMAND_CHAIN);
		noGoMats.add(Material.COMMAND_MINECART);
		noGoMats.add(Material.COMMAND_REPEATING);
		noGoMats.add(Material.DAYLIGHT_DETECTOR);
		noGoMats.add(Material.DAYLIGHT_DETECTOR_INVERTED);
		noGoMats.add(Material.DIODE);
		noGoMats.add(Material.DIODE_BLOCK_OFF);
		noGoMats.add(Material.DIODE_BLOCK_ON);
		noGoMats.add(Material.DISPENSER);
		noGoMats.add(Material.DOUBLE_PLANT);
		noGoMats.add(Material.DROPPER);
		noGoMats.add(Material.END_CRYSTAL);
		noGoMats.add(Material.END_GATEWAY);
		noGoMats.add(Material.ENDER_PORTAL);
		noGoMats.add(Material.ENDER_PORTAL_FRAME);
		noGoMats.add(Material.FIRE);
		noGoMats.add(Material.FURNACE);
		noGoMats.add(Material.HOPPER);
		noGoMats.add(Material.HOPPER_MINECART);
		noGoMats.add(Material.ITEM_FRAME);
		noGoMats.add(Material.LEVER);
		noGoMats.add(Material.PAINTING);
		noGoMats.add(Material.PISTON_BASE);
		noGoMats.add(Material.PISTON_EXTENSION);
		noGoMats.add(Material.PISTON_MOVING_PIECE);
		noGoMats.add(Material.PISTON_STICKY_BASE);
		noGoMats.add(Material.POWERED_RAIL);
		noGoMats.add(Material.REDSTONE);
		noGoMats.add(Material.REDSTONE_BLOCK);
		noGoMats.add(Material.REDSTONE_COMPARATOR);
		noGoMats.add(Material.REDSTONE_COMPARATOR_OFF);
		noGoMats.add(Material.REDSTONE_COMPARATOR_ON);
		noGoMats.add(Material.REDSTONE_TORCH_ON);
		noGoMats.add(Material.REDSTONE_TORCH_OFF);
		noGoMats.add(Material.REDSTONE_WIRE);
		noGoMats.add(Material.SAPLING);
		noGoMats.add(Material.SIGN_POST);
		noGoMats.add(Material.SKULL);
		noGoMats.add(Material.STANDING_BANNER);
		noGoMats.add(Material.STORAGE_MINECART);
		noGoMats.add(Material.STRUCTURE_BLOCK);
		noGoMats.add(Material.TRIPWIRE);
		noGoMats.add(Material.TRIPWIRE_HOOK);
		noGoMats.add(Material.VINE);
		noGoMats.add(Material.WALL_BANNER);
	}
	
  //"light" command processing
	public void lightUp(Player player) {
		
		Location underfoot = player.getLocation().add(0, -1, 0);
		Material mat = underfoot.getBlock().getType();
		
		if ( !noGoMats.contains(mat) ) {
			
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
