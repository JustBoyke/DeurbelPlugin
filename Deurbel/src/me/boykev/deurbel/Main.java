package me.boykev.deurbel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		PluginManager pm = Bukkit.getPluginManager();
		System.out.println(ChatColor.GREEN + "Deurbel plugin opgestart!");
		
		pm.registerEvents(new SignCreate(this), this);
		pm.registerEvents(new SignClick(this), this);
		
	}
	
	public void onDisable() {
		System.out.println(ChatColor.RED + "Deurbel plugin afgesloten!");
	}
	
	
	
	
}
