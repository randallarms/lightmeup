// ========================================================================
// |LIGHTMEUP v1.3
// | by Kraken | https://www.spigotmc.org/resources/lightmeup.42376/
// | code inspired by various Bukkit & Spigot devs -- thank you. 
// |
// | Always free & open-source! If this plugin is being sold or re-branded,
// | please let me know on the SpigotMC site, or wherever you can. Thanks!
// | Source code: https://github.com/randallarms/lightmeup
// ========================================================================

package com.kraken.lightmeup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public class Main extends JavaPlugin {
  	
	public String VERSION = "1.3";
	
    private File optionsFile = new File("plugins/LightMeUp", "options.yml");
    private FileConfiguration options = YamlConfiguration.loadConfiguration(optionsFile);
	
	LightProcessing lp = new LightProcessing(this);
    
	boolean enabled = true;
	boolean opRequired = false;
	boolean whitelist = false;
	
	ArrayList<Player> isLit = new ArrayList<Player>();
	ArrayList<String> isAllowed = new ArrayList<String>();
	
	@Override
    public void onEnable() {
    	
    	getLogger().info("LightMeUp has been enabled.");
		PluginManager pm = getServer().getPluginManager();
		LMUListener listener = new LMUListener( getInstance(), lp );
		pm.registerEvents(listener, this);
		
		if ( !options.getBoolean("loaded") ) {
			
			options.set("loaded", true);
			options.set("enabled", true);
			options.set("opRequired", false);
			options.set("whitelist", false);
			
	        saveOptions();
	        
		}

        enabled = options.getBoolean("enabled");
        opRequired = options.getBoolean("opRequired");
        
        for (String id : getConfig().getKeys(false) ) {
        	
        	if ( getConfig().getBoolean(id + ".allowed") ) {
        		
        		isAllowed.add(id);
        		
        	}
        	
        }
		
    }
    
    @Override
    public void onDisable() {
    	
        getLogger().info("LightMeUp has been disabled.");
        
    }
    
    public Main getInstance() {
    	return this;
    }
    
    public void playerAllowed(String UUIDString, boolean allowed) {
    	
      if (allowed) {
        isAllowed.add(UUIDString);
      } else {
        isAllowed.remove(UUIDString);
      }
      
    }
    
    public boolean saveOptions() {
    	
        try {
			options.save(optionsFile);
			return true;
		} catch (IOException e) {
			System.out.println("[LIGHTMEUP] Failed to save options.yml file, expect errors.");
			return false;
		}
        
    }
    
  //LMU commands
    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
      //Player commands
        if ( sender instanceof Player ) {
        	
        	String command = cmd.getName();
    		Player player = (Player) sender;
    		String UUIDString = player.getUniqueId().toString();
     	
        	switch ( command.toLowerCase() ) {
        	
	        //Command: version        
	    		case "lmu":
	    			
	    			if (player.isOp()) {
      	        	  
        	            if (args.length == 1) {
        	            	
        	            	switch (args[0]) {
        	            	
        	            		case "on":
        	            		case "enable":
        	            		case "true":
        	            			options.set("enabled", true);
        	            			enabled = true;
        	            			saveOptions();
        	            			
        	            		case "off":
        	            		case "disable":
        	            		case "false":
        	            			options.set("enabled", false);
        	            			enabled = false;
        	            			saveOptions();
        	            			
        	            		default:
        	            			player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "Try \"/lmu <on/off>\".");
        	            			return true;
        	            	
        	            	}
        	            	
        	            } else {
        	            	player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "CURRENT: LightMeUp v" + VERSION + " (release)");
        	                return true;
        	            }
        	            
	    			}
        	
			  //Command: jump
        	    case "light":
        	    	
                	if ( opRequired && !player.isOp() ) {
                		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You do not have light privileges.");
                        return true;
                	} else if ( !enabled ) {
        	    		
        	    		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "LMU is currently disabled.");
        	    		return true;
        	    		
        	    	} else if ( !whitelist || isAllowed.contains(UUIDString) ) {
			        	
			            if ( isLit.contains(player) ) {
			            	
			              lp.lightOff(player);
			              isLit.remove(player);
			              player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Lights off.");
			              
			            } else {
			            	
			              lp.lightUp(player);
			              isLit.add(player);
			              player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You are lit!");
			              
			            }
			            
			        } else {
			        	player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You do not have permission to use this command.");
			        }
        	    	
			        return true;
			        
			  //Command: allowLight
        	    case "allowLight":
        	    case "allowlight":
        	    case "allowLMU":
        	    case "allowlmu":
			        
        	          Player targetPlayer;
        	          String targetUUID;
        	          
        	          if (player.isOp()) {
        	        	  
        	            if (args.length == 1) {
        	            	
        	            	if ( args[0].equals("*") ) {
        	            		
        	            		if ( options.getBoolean("whitelist") ) {
        	            			
        	            			whitelist = false;
        	            			options.set("whitelist", false);
        	            			saveOptions();
            	            		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Whitelisting is now disabled.");
            	            		return true;
            	            		
        	            		} else {
        	            			
        	            			whitelist = true;
        	            			options.set("whitelist", true);
        	            			saveOptions();
            	            		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Whitelisting is now enabled.");
            	            		return true;
            	            		
        	            		}
        	            		
        	            		
        	            		
        	            	}
        	            	
        	            	try {
        	            		targetPlayer = getServer().getPlayerExact(args[0]);
        	            		targetUUID = targetPlayer.getUniqueId().toString();
        	            	} catch (NullPointerException npe1) {
        	            		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Player not found online.");
        	            		return true;
        	            	}
        	              
        	            	if ( !isAllowed.contains(targetUUID) ) {
        	            	  
        	            		getConfig().set(targetUUID + ".allowed", true);
        	            		isAllowed.add(targetUUID);
        	            		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Added " + args[0] + " to the LMU whitelist.");
        	                
        	            	} else {
        	            	  
        	            		getConfig().set(targetUUID + ".allowed", false);
        	            		isAllowed.remove(targetUUID);
        	            		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Removed " + args[0] + " from the LMU whitelist.");
        	                
        	            	}
        	              
        	            	saveConfig();
        	            	return true;
        	              
        	            }
        	            
        	            player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Try entering \"/allowLight <player>\".");
        	            return true;
        	            
        	          }
        	          
        	          player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You do not have permission to use this command.");
        	          return true;
			        
			  //Command: opRequired
        	    case "opRequiredLMU":
        	    case "oprequiredlmu":
        	    case "opReqLMU":
        	    case "opreqlmu":
        			  
        	    	if ( args.length == 1 ) {
        	    		switch ( args[0].toLowerCase() ) {
        	    			case "on":
        	    			case "enable":
        	    			case "enabled":
        	    			case "true":
        	    				options.set("opRequired", true);
        	    				opRequired = true;
        	    				saveOptions();
        	    				return true;
        	    			case "off":
        	    			case "disable":
        	    			case "disabled":
        	    			case "false":
        	    				options.set("opRequired", false);
        	    				opRequired = false;
        	    				saveOptions();
        	    				return true;
        	    			default:
        	    				player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Try entering \"/opReq <on/off>\".");
        	        	    	return true;
        	    		}
        	    	}
        	    	
        	    	player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Try entering \"/opReq <on/off>\".");
        	    	return true;
			        
			    default:
			    	  
			        player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Command not recognized.");
			        return true;
			    
	        }
        
        }
        
        return true;
        
    }
    
}
