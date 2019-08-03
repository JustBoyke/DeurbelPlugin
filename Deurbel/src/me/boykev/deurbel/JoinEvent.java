package me.boykev.deurbel;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class JoinEvent implements Listener{
	
	private Main instance;

	public JoinEvent(Main main) {
		this.instance = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(instance.Status.equals("Abuse") && e.getPlayer().hasPermission("inkoop.admin") || instance.Status.equals("Abuse") && e.getPlayer().isOp()) {
			Bukkit.broadcastMessage(ChatColor.DARK_RED+ "Je maakt misbruik van de Deurbel Plugin, neem contact op met de developer.");
			e.getPlayer().sendMessage(ChatColor.RED + "De eigenaar van de Deurbel Plugin heeft de plugin voor deze server geblokkeerd in verband met Abuse. contact op met de developer");
			return;
		}
		if(instance.Status.equals("Edit") && e.getPlayer().hasPermission("inkoop.admin") || instance.Status.equals("Edit") && e.getPlayer().isOp()) {
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "Je hebt wijzigingen aangebracht in de Deurbel Plugin, dit is niet toegestaan neem contact op met de developer.");
			e.getPlayer().sendMessage(ChatColor.RED + "De eigenaar van de Deurbel Plugin heeft de plugin voor deze server geblokkeerd in verband met Abuse. Neem contact op met de eigenaar van de plugin");
			return;
		}
		return;
	}

}
