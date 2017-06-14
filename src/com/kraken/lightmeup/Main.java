// ========================================================================
// |LIGHTMEUP v0.1
// | by Kraken | unpublished
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

import org.bukkit.ChatColor;

public class Main extends JavaPlugin {
  	
	public String VERSION = "0.1";
	
    private File optionsFile = new File("plugins/LightMeUp", "options.yml");
    private FileConfiguration options = YamlConfiguration.loadConfiguration(optionsFile);
	
	LightProcessing lp = new LightProcessing(this);
    
	boolean opRequired = true;
	
	@Override
    public void onEnable() {
    	
    	getLogger().info("LightMeUp has been enabled.");
		PluginManager pm = getServer().getPluginManager();
		LMUListener listener = new LMUListener( getInstance(), lp );
		pm.registerEvents(listener, this);

        opRequired = options.getBoolean("opRequired");
		
    }
    
    @Override
    public void onDisable() {
    	
        getLogger().info("LightMeUp has been disabled.");
        
    }
    
    public Main getInstance() {
    	return this;
    }
    
  //LMU commands
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    	String command = cmd.getName();
		Player player = (Player) sender;
        
      //Player commands
        if ( sender instanceof Player ) {
        
        	if ( opRequired && !player.isOp() ) {
        		player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You do not have teleport privileges.");
                return true;
        	}
        	
        	switch (command) {
        	
	        //Command: version        
	    		case "lmu":
	    			
	    			player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "CURRENT: LightMeUp v" + VERSION + " (release)");
	                return true;
        	
			  //Command: jump
        	    case "light":
        			  
        			lp.lightUp(player);
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
	    	    				try {
	    	    			        options.save(optionsFile);
	    	    				} catch (IOException ioe2) {
	    	    					// No need to fuss!
	    	    				}
        	    				return true;
        	    			case "off":
        	    			case "disable":
        	    			case "disabled":
        	    			case "false":
        	    				options.set("opRequired", false);
        	    				opRequired = false;
        	    				try {
	    	    			        options.save(optionsFile);
	    	    				} catch (IOException ioe3) {
	    	    					// No need to fuss!
	    	    				}
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
