// ========================================================================
// |LIGHTMEUP v1.3.1
// | by Kraken | https://www.spigotmc.org/resources/lightmeup.42376/
// | code inspired by various Bukkit & Spigot devs -- thank you. 
// |
// | Always free & open-source! If this plugin is being sold or re-branded,
// | please let me know on the SpigotMC site, or wherever you can. Thanks!
// | Source code: https://github.com/randallarms/lightmeup
// ========================================================================

package com.kraken.lightmeup;

import org.bukkit.Bukkit;
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

public class Main extends JavaPlugin {
  	
	public String VERSION = "1.3.1";
	
    private File optionsFile = new File("plugins/LightMeUp", "options.yml");
    private FileConfiguration options = YamlConfiguration.loadConfiguration(optionsFile);
	
	LightProcessing lp = new LightProcessing(this);
	Messages messenger;
    
	boolean enabled = true;
	boolean opRequired = false;
	boolean whitelist = false;
	boolean silentMode = false;
	
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
		
		this.messenger = new Messages("English", silentMode, VERSION);

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
    
    public void msg(Player player, String msg, boolean isPlayer) {
    	messenger.makeMsg(player, msg, isPlayer);
    }
    
  //LMU commands
    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
    	boolean isPlayer = false;
    	
    	Player player;
    	String command = cmd.getName();
    	
	    if ( sender instanceof Player ) {
			isPlayer = true;
			player = (Player) sender;
	    } else {
	    	isPlayer = false;
	    	player = (Player) Bukkit.getServer().getPlayerExact("Octopus__");
	    }
	    	
    	switch ( command.toLowerCase() ) {
    	
          //Command: lmu        
    		case "lmu":
    			
    			if ( ( isPlayer && !player.isOp() ) || ( args.length != 1 && args.length != 2 ) ) {
    				
    				msg(player, "version", isPlayer);
	                return true;
	                
    			} else if ( args.length == 1 ) {
    				
    				switch ( args[0].toLowerCase() ) {
	            	
	            		case "on":
	            		case "enable":
	            		case "true":
	            			options.set("enabled", true);
	            			enabled = true;
	            			saveOptions();
	            			msg(player, "lmuEnabled", isPlayer);
	            			
	            		case "off":
	            		case "disable":
	            		case "false":
	            			options.set("enabled", false);
	            			enabled = false;
	            			saveOptions();
	            			msg(player, "lmuDisabled", isPlayer);
	            			
	            		default:
	            			msg(player, "lmuCmdError", isPlayer);
	            			return true;
    				
    				}
    				
    			} else if ( args.length == 2 ) {
    				
	    			switch ( args[0].toLowerCase() ) {
	    				
	    			  //Command: lmu opreq
		        	    case "oprequired":
		        	    case "opreq":
		        	    		
	        	    		switch ( args[1].toLowerCase() ) {
	        	    		
	        	    			case "on":
	        	    			case "enable":
	        	    			case "enabled":
	        	    			case "true":
	        	    				options.set("opRequired", true);
	        	    				opRequired = true;
	        	    				saveOptions();
	        	    				msg(player, "opReqEnabled", isPlayer);
	        	    				return true;
	        	    				
	        	    			case "off":
	        	    			case "disable":
	        	    			case "disabled":
	        	    			case "false":
	        	    				options.set("opRequired", false);
	        	    				opRequired = false;
	        	    				saveOptions();
	        	    				msg(player, "opReqDisabled", isPlayer);
	        	    				return true;
	        	    				
	        	    			default:
	        	    				msg(player, "opReqCmdError", isPlayer);
	        	        	    	return true;
	        	        	    	
	        	    		}
	        	    		
	  	    		  //Command: lmu whitelist
		        	    case "whitelist":
		        	    case "whitelisting":
	        	    		
		        			switch ( args[1].toLowerCase() ) {
		    	            	
			            		case "on":
			            		case "enable":
			            		case "true":
			            			options.set("whitelist", true);
			            			whitelist = true;
			            			saveOptions();
			            			msg(player, "whitelistEnabled", isPlayer);
			            			
			            		case "off":
			            		case "disable":
			            		case "false":
			            			options.set("whitelist", false);
			            			whitelist = false;
			            			saveOptions();
			            			msg(player, "whitelistDisabled", isPlayer);
			            			
			            		default:
			            			msg(player, "whitelistCmdError", isPlayer);
			            			return true;
		    				
		    				}
		        	    	
	    			}
    				
    			}
    	
		  //Command: light
    	    case "light":
    	    	
    	    	if ( isPlayer ) {
    	    		
	            	if ( opRequired && !player.isOp() ) {
	            		msg(player, "lightCmdError", isPlayer);
	                    return true;
	            	} else if ( !enabled ) {
	    	    		
	    	    		msg(player, "lmuDisabledError", isPlayer);
	    	    		return true;
	    	    		
	    	    	} else if ( !whitelist || isAllowed.contains(player.getUniqueId().toString()) ) {
			        	
			            if ( isLit.contains(player) ) {
			            	
			              lp.lightOff(player);
			              isLit.remove(player);
			              msg(player, "lightsOff", isPlayer);
			              
			            } else {
			            	
			              lp.lightUp(player);
			              isLit.add(player);
			              msg(player, "lightsOn", isPlayer);
			              
			            }
			            
			        } else {
			        	msg(player, "generalCmdError", isPlayer);
			        }
	            	
    	    	} else {
    	    		msg(player, "playerOnlyError", isPlayer);
    	    	}
    	    	
		        return true;
		        
		  //Command: allowLight
    	    case "allowlight":
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
        	            		msg(player, "whitelistDisabled", isPlayer);
        	            		return true;
        	            		
    	            		} else {
    	            			
    	            			whitelist = true;
    	            			options.set("whitelist", true);
    	            			saveOptions();
        	            		msg(player, "whitelistEnabled", isPlayer);
        	            		return true;
        	            		
    	            		}
    	            		
    	            	}
    	            	
    	            	try {
    	            		targetPlayer = getServer().getPlayerExact(args[0]);
    	            		targetUUID = targetPlayer.getUniqueId().toString();
    	            	} catch (NullPointerException npe1) {
    	            		msg(player, "playerNotFoundError", isPlayer);
    	            		return true;
    	            	}
    	              
    	            	if ( !isAllowed.contains(targetUUID) ) {
    	            	  
    	            		getConfig().set(targetUUID + ".allowed", true);
    	            		isAllowed.add(targetUUID);
    	            		msg(player, "whitelistAdded", isPlayer);
    	                
    	            	} else {
    	            	  
    	            		getConfig().set(targetUUID + ".allowed", false);
    	            		isAllowed.remove(targetUUID);
    	            		msg(player, "whitelistRemoved", isPlayer);
    	                
    	            	}
    	              
    	            	saveConfig();
    	            	return true;
    	              
    	            }
    	            
    	            msg(player, "allowLightCmdError", isPlayer);
    	            return true;
    	            
    	          }
    	          
    	          msg(player, "generalCmdError", isPlayer);
    	          return true;
		        
		    default:
		    	  
		        msg(player, "unrecognizedCmdError", isPlayer);
		        return true;
		    
        }
	    
	}
    
}
