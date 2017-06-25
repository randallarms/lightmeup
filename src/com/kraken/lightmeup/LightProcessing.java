package com.kraken.lightmeup;

import java.util.ArrayList;
import java.util.Arrays;
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
		noGoMats.addAll(Arrays.asList(Material.AIR, Material.SEA_LANTERN, Material.CHEST, Material.TRAPPED_CHEST, Material.SIGN,
										Material.WALL_SIGN, Material.BEACON, Material.ENCHANTMENT_TABLE, Material.WORKBENCH,
										Material.JUKEBOX, Material.NOTE_BLOCK, Material.ANVIL, Material.ARMOR_STAND, Material.BANNER,
										Material.BARRIER, Material.BEDROCK, Material.BED_BLOCK, Material.BED, Material.BREWING_STAND,
										Material.BURNING_FURNACE, Material.CAKE_BLOCK, Material.COMMAND, Material.COMMAND_CHAIN,
										Material.COMMAND_MINECART, Material.COMMAND_REPEATING, Material.DAYLIGHT_DETECTOR,
										Material.DAYLIGHT_DETECTOR_INVERTED, Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON,
										Material.DISPENSER, Material.DOUBLE_PLANT, Material.DROPPER, Material.END_CRYSTAL, Material.END_GATEWAY,
										Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME, Material.FIRE, Material.FURNACE, Material.HOPPER,
										Material.HOPPER_MINECART, Material.ITEM_FRAME, Material.LEVER, Material.PAINTING, Material.PISTON_BASE,
										Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE, Material.PISTON_STICKY_BASE, Material.POWERED_RAIL,
										Material.REDSTONE, Material.REDSTONE_BLOCK, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF,
										Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_TORCH_ON, Material.REDSTONE_TORCH_ON, Material.REDSTONE_WIRE,
										Material.SAPLING, Material.SIGN_POST, Material.SKULL, Material.STANDING_BANNER, Material.STORAGE_MINECART,
										Material.STRUCTURE_BLOCK, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.VINE, Material.WALL_BANNER));
		
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
		
		if ( !litPlayers.contains( player.getUniqueId().toString() ) ) {
			litPlayers.add( player.getUniqueId().toString() );
		}
		
	}
	
	public void checkBlock (Player player, Location underfoot) {
		
		if ( litPlayers.contains( player.getUniqueId().toString() ) ) {
			
			Location savedLoc = litBlocks.get(player);
			
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
		savedLoc.getBlock().setData(data);
		litTypes.remove(savedLoc);
		litData.remove(savedLoc);
		
	}
	
}
