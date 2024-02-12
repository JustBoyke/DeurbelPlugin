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
	
	public void doBell(Player p, Location signloc, Player cp, String caller) {
		Location pl = p.getLocation();
		Location sl = signloc;
		World w = sl.getWorld();
		
		p.playSound(pl, "minecraft:block.bell.use", 3, 1);
		w.playSound(sl, "minecraft:block.bell.use", 2, 1);
		p.sendMessage(ChatColor.BLUE + "er wordt aangebeld bij je bel op: X " + ChatColor.RED + sl.getBlockX() + ChatColor.BLUE + " Z " + ChatColor.RED + sl.getBlockZ() + ChatColor.BLUE + " Door: " + ChatColor.RED + cp.getName());
		cp.sendMessage(ChatColor.BLUE + "Je hebt aangebeld bij " + ChatColor.RED + caller);
		cooldown.put(cp.getName(), System.currentTimeMillis());
	}
	public void doBellNoNotice(Player p, Location signloc, Player cp, String caller) {
		Location pl = p.getLocation();
		Location sl = signloc;
		World w = sl.getWorld();
		
		p.playSound(pl, "minecraft:block.bell.use", 3, 1);
		w.playSound(sl, "minecraft:block.bell.use", 2, 1);
		p.sendMessage(ChatColor.BLUE + "er wordt aangebeld bij de " + caller + " op: X " + ChatColor.RED + sl.getBlockX() + ChatColor.BLUE + " Z " + ChatColor.RED + sl.getBlockZ() + ChatColor.BLUE + " Door: " + ChatColor.RED + cp.getName());
		cooldown.put(cp.getName(), System.currentTimeMillis());
	}
	
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
						if(sign.getLine(2).toString().equalsIgnoreCase("bank")) {
							if(cooldown.containsKey(cp.getName())) {
								long left = ((cooldown.get(cp.getName())/1000)+cooldowntime) - (System.currentTimeMillis()/1000);
								if(left > 0) {
									cp.sendMessage(ChatColor.RED + "Je moet nog " + left + " seconden wachten tot je weer kunt aanbellen!!");
									return;
								}
							}
							cp.sendMessage(ChatColor.BLUE + "Je hebt aangebeld bij " + ChatColor.RED + "de bank");
							Player boykev = Bukkit.getPlayer("boykev");
							Player teun = Bukkit.getPlayer("TVR_404");
							if(boykev != null && boykev.isOnline()) {
								this.doBellNoNotice(boykev, sign.getLocation(), cp, "bank");
							}
							if(teun != null && teun.isOnline()) {
								this.doBellNoNotice(teun, sign.getLocation(), cp, "bank");
							}
							return;
						}
						if(sign.getLine(2).toString().equalsIgnoreCase("overheid")) {
							if(cooldown.containsKey(cp.getName())) {
								long left = ((cooldown.get(cp.getName())/1000)+cooldowntime) - (System.currentTimeMillis()/1000);
								if(left > 0) {
									cp.sendMessage(ChatColor.RED + "Je moet nog " + left + " seconden wachten tot je weer kunt aanbellen!!");
									return;
								}
							}
							cp.sendMessage(ChatColor.BLUE + "Je hebt aangebeld bij " + ChatColor.RED + "de overheid");
							Player boykev = Bukkit.getPlayer("boykev");
							Player teun = Bukkit.getPlayer("TVR_404");
							if(boykev != null && boykev.isOnline()) {
								this.doBellNoNotice(boykev, sign.getLocation(), cp, "overheid");
							}
							if(teun != null && teun.isOnline()) {
								this.doBellNoNotice(teun, sign.getLocation(), cp, "overheid");
							}
							return;
						}
						cp.sendMessage(ChatColor.RED + "Deze speler is niet online daarom werkt de bel helaas niet :(!");
						return;
					}
					if(cooldown.containsKey(cp.getName())) {
						long left = ((cooldown.get(cp.getName())/1000)+cooldowntime) - (System.currentTimeMillis()/1000);
						if(left > 0) {
							cp.sendMessage(ChatColor.RED + "Je moet nog " + left + " seconden wachten tot je weer kunt aanbellen!!");
							return;
						}
					}
					
					this.doBell(p, sign.getLocation(), cp, p.getName().toString());
					
					
				}
				return;
			}//Check for sign
			return;
		}//Right Click Block
		return;
	}//Interact Event

}
