package me.boykev.deurbel;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class SignClick implements Listener{
	
	private Main instance;
	public SignClick(Main main) {
		this.instance = main;
	}
	
	public String PREFIX = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "Deurbel" + ChatColor.WHITE + "]";
	public HashMap<String, Long> cooldown = new HashMap<String, Long>();
	private int cooldowntime = 6;
	
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(SignCreate.items.contains(e.getClickedBlock().getType())) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				if(sign.getLine(0).equalsIgnoreCase(PREFIX)) {
					if(sign.getLine(2).isEmpty()) {
						e.getPlayer().sendMessage(ChatColor.RED + "Er is iets fout gegaan, error code:" + ChatColor.AQUA + "P_NOT_FOUND");
						return;
					}//niet gevonden item
					Player cp = e.getPlayer();
					Player p = Bukkit.getPlayer(sign.getLine(2).toString());
					if(p == null) {
						cp.sendMessage(ChatColor.RED + "Deze speler is niet online daarom werkt de bel helaas niet :(");
						return;
					}
					if(cooldown.containsKey(cp.getName())) {
						long left = ((cooldown.get(cp.getName())/1000)+cooldowntime) - (System.currentTimeMillis()/1000);
						if(left > 0) {
							cp.sendMessage(ChatColor.RED + "Je moet nog " + left + " seconden wachten tot je weer kunt aanbellen!!");
							return;
						}
					}
					
					
					Location pl = p.getLocation();
					Location sl = sign.getLocation();
					World w = sl.getWorld();
					
					p.playSound(pl, "deurbel", 3, 2);
					w.playSound(sl, "deurbel", 2, 2);
					p.sendMessage(ChatColor.BLUE + "er wordt aangebeld bij je huis op: X " + ChatColor.RED + sl.getBlockX() + ChatColor.BLUE + " Z " + ChatColor.RED + sl.getBlockZ() + ChatColor.BLUE + " Door: " + ChatColor.RED + cp.getName());
					cp.sendMessage(ChatColor.BLUE + "Je hebt aangebeld bij " + ChatColor.RED + sign.getLine(2));
					cooldown.put(cp.getName(), System.currentTimeMillis());
					
				}
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "BEL")) {
					Player cp = e.getPlayer();
					if(cooldown.containsKey(cp.getName())) {
						long left = ((cooldown.get(cp.getName())/1000)+cooldowntime) - (System.currentTimeMillis()/1000);
						if(left > 0) {
							cp.sendMessage(ChatColor.RED + "Je moet nog " + left + " seconden wachten tot je weer kunt bellen!!");
							return;
						}
					}
					
					String link = sign.getLine(3);
					if(link.isEmpty()) {
						cp.sendMessage(ChatColor.RED + "Deze bel is niet juist ingesteld!");
						return;
					}
					Location sl = sign.getLocation();
					World w = sl.getWorld();
					com.bergerkiller.bukkit.sl.API.Variables.get(link).set(ChatColor.DARK_GREEN + "Volgende!");
					
					w.playSound(sl, "deurbel", 2, 2);
					cp.sendMessage(ChatColor.BLUE + "Je hebt de bel over laten gaan");
					cooldown.put(cp.getName(), System.currentTimeMillis());
					new BukkitRunnable() {
						@Override
						public void run() {
							com.bergerkiller.bukkit.sl.API.Variables.get(link).set(ChatColor.DARK_RED + "BEZET");
						}
					}.runTaskLater(instance, 100);
					
				}//Check sign for PREFIX
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "GBEL")) {
					Player cp = e.getPlayer();
					if(cooldown.containsKey(cp.getName())) {
						long left = ((cooldown.get(cp.getName())/1000)+cooldowntime) - (System.currentTimeMillis()/1000);
						if(left > 0) {
							cp.sendMessage(ChatColor.RED + "Je moet nog " + left + " seconden wachten tot je weer kunt bellen!!");
							return;
						}
					}
					
					String link = sign.getLine(3);
					if(link.isEmpty()) {
						cp.sendMessage(ChatColor.RED + "Deze bel is niet juist ingesteld!");
						return;
					}
					Location sl = new Location(sign.getWorld(), -6,74,197);
					Location sl2 = new Location(sign.getWorld(), 10,74,197);
					World w = sl.getWorld();
					World w2 = sl2.getWorld();
					com.bergerkiller.bukkit.sl.API.Variables.get(link).set(ChatColor.DARK_GREEN + "Volgende!");
					
					w.playSound(sl, "deurbel", 2, 2);
					w2.playSound(sl,"deurbel", 2, 2);
					cp.sendMessage(ChatColor.BLUE + "Je hebt de bel over laten gaan");
					cooldown.put(cp.getName(), System.currentTimeMillis());
					new BukkitRunnable() {
						@Override
						public void run() {
							com.bergerkiller.bukkit.sl.API.Variables.get(link).set(ChatColor.DARK_RED + "BEZET");
						}
					}.runTaskLater(instance, 100);
					
				}//Check sign for PREFIX
				return;
			}//Check for sign
			return;
		}//Right Click Block
		return;
	}//Interact Event

}
