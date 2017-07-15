package com.kraken.lightmeup;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {

	String language;
	boolean silentMode;
	String VERSION = "1.0";
	
	public Messages(String language, boolean silentMode, String VERSION) {
        this.language = language;
        this.silentMode = silentMode;
        this.VERSION = VERSION;
    }
	
	public void silence(boolean setting) {
		this.silentMode = setting;
	}
	
	public void makeMsg(Player player, String msg, boolean isPlayer) {
		
		if ( this.silentMode ) {
			return;
		}
		
		switch (language) {
		
			case "Spanish":
			case "spanish":
				
				//Start of Spanish messages
				switch (msg) {
				
					case "errorIllegalCommand":
						if (isPlayer) {
							player.sendMessage(ChatColor.RED + "No reconocido el comando, o no tienes el permiso.");
						} else {
							System.out.println("[LIGHTMEUP] No reconocido el comando, o no tienes el permiso.");
						}
						
						break;
						
					default:
						break;
					
				} 
				
				break;
			  // End of Spanish messages
			
			default:
		
			  //Start of English messages
				switch (msg) {
				
					case "generalCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.RED + "Your command was not recognized, or you have insufficient permissions.");
						} else {
							System.out.println("[LIGHTMEUP] Your command was not recognized, or you have insufficient permissions.");
						}
						
						break;
						
					case "version":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "CURRENT: LightMeUp v" + VERSION + " (release)");
						} else {
							System.out.println("[LIGHTMEUP] CURRENT: LightMeUp v" + VERSION + " (release)");
						}
						
						break;
						
					case "lmuEnabled":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "LightMeUp is now enabled.");
						} else {
							System.out.println("[LIGHTMEUP] LightMeUp is now enabled.");
						}
						
						break;
						
					case "lmuDisabled":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "LightMeUp is now disabled.");
						} else {
							System.out.println("[LIGHTMEUP] LightMeUp is now disabled.");
						}
						
						break;
						
					case "lmuCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "Try \"/lmu <on/off>\".");
						} else {
							System.out.println("[LIGHTMEUP] Try \"/lmu <on/off>\".");
						}
						
						break;
						
					case "opReqEnabled":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "The OP requirement is now enabled.");
						} else {
							System.out.println("[LIGHTMEUP] The OP requirement is now enabled.");
						}
						
						break;
						
					case "opReqDisabled":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + "The OP requirement is now disabled.");
						} else {
							System.out.println("[LIGHTMEUP] The OP requirement is now disabled.");
						}
						
						break;
						
					case "opReqCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Try entering \"/lmu opReq <on/off>\".");
						} else {
							System.out.println("[LIGHTMEUP] Try entering \"/lmu opReq <on/off>\".");
						}
						
						break;
						
					case "lightCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You do not have light privileges.");
						} else {
							System.out.println("[LIGHTMEUP] You do not have light privileges.");
						}
						
						break;
						
					case "lmuDisabledError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "LMU is currently disabled.");
						} else {
							System.out.println("[LIGHTMEUP] LightMeUp is currently disabled.");
						}
						
						break;
						
					case "lightsOn":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "You are lit!");
						} else {
							System.out.println("[LIGHTMEUP] Lights on?");
						}
						
						break;
						
					case "lightsOff":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Lights off.");
						} else {
							System.out.println("[LIGHTMEUP] Lights off?");
						}
						
						break;
						
					case "playerOnlyError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Unknown error.");
						} else {
							System.out.println("[LIGHTMEUP] This is a player-only command.");
						}
						
						break;
						
					case "whitelistEnabled":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Whitelisting is now enabled.");
						} else {
							System.out.println("[LIGHTMEUP] Whitelisting is now enabled.");
						}
						
						break;
						
					case "whitelistDisabled":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Whitelisting is now disabled.");
						} else {
							System.out.println("[LIGHTMEUP] Whitelisting is now disabled.");
						}
						
						break;
						
					case "whitelistCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Try entering \"/lmu whitelist <on/off>\".");
						} else {
							System.out.println("[LIGHTMEUP] Try entering \"/lmu whitelist <on/off>\".");
						}
						
						break;
						
					case "playerNotFoundError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Player not found online.");
						} else {
							System.out.println("[LIGHTMEUP] Player not found online.");
						}
						
						break;
						
					case "whitelistAdded":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Added player to the LMU whitelist.");
						} else {
							System.out.println("[LIGHTMEUP] Added player to the LMU whitelist.");
						}
						
						break;
						
					case "whitelistRemoved":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Removed player from the LMU whitelist.");
						} else {
							System.out.println("[LIGHTMEUP] Removed player from the LMU whitelist.");
						}
						
						break;
						
					case "allowLightCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Try entering \"/allowLight <player>\", or just \"/allowLight\" to toggle whitelisting.");
						} else {
							System.out.println("[LIGHTMEUP] Try entering \"/allowLight <player>\", or just \"/allowLight\" to toggle whitelisting.");
						}
						
						break;
						
					case "unrecognizedCmdError":
						if (isPlayer) {
							player.sendMessage(ChatColor.GOLD + "[LMU]" + ChatColor.GRAY + " | " + "Command not recognized.");
						} else {
							System.out.println("[LIGHTMEUP] Command not recognized.");
						}
						
						break;
						
					default:
						break;
					
				} 
				
				break;
			  // End of English messages
				
		}
	
	}
		
}
