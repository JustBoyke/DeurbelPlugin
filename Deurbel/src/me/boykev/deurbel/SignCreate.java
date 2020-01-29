package me.boykev.deurbel;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import net.md_5.bungee.api.ChatColor;

public class SignCreate implements Listener {
	
	@SuppressWarnings("unused")
	private Main instance;
	public String PREFIX = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "Deurbel" + ChatColor.WHITE + "]";
	public static ArrayList<Material> items = new ArrayList<Material>();
	
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
	
	public void makeItems() {
		items.add(Material.ACACIA_SIGN);
		items.add(Material.ACACIA_WALL_SIGN);
		items.add(Material.BIRCH_SIGN);
		items.add(Material.BIRCH_WALL_SIGN);
		items.add(Material.DARK_OAK_SIGN);
		items.add(Material.DARK_OAK_WALL_SIGN);
		items.add(Material.JUNGLE_SIGN);
		items.add(Material.JUNGLE_WALL_SIGN);
		items.add(Material.OAK_SIGN);
		items.add(Material.OAK_WALL_SIGN);
		items.add(Material.SPRUCE_SIGN);
		items.add(Material.SPRUCE_WALL_SIGN);
	}
	
}
