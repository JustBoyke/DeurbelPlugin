package me.boykev.deurbel;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import net.md_5.bungee.api.ChatColor;

public class SignCreate implements Listener {
	
	@SuppressWarnings("unused")
	private Main instance;
	public String PREFIX = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "Deurbel" + ChatColor.WHITE + "]";
	
	public SignCreate(Main main) {
		this.instance = main;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(!e.getPlayer().hasPermission("sign.deurbel")) {
            e.getPlayer().sendMessage(ChatColor.RED + "Je hebt hier geen rechten voor!");
            return;
        }
        if(e.getLine(0).equalsIgnoreCase("[Deurbel]")) {
        	e.setLine(0, PREFIX);
            e.setLine(1, ChatColor.GRAY + "[O]");
            return;
        }
	}
	
}
