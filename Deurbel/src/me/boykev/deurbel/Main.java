package me.boykev.deurbel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;


@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener {
	private ConfigManager cm;
	public static String PREFIX; 
	public String Status;
	private SignCreate sc;
	
	@Override
	public void onEnable() {
		sc = new SignCreate(this);
		//Initialize Plugin Manager
		
		
		PluginDescriptionFile pdf = this.getDescription();
		if(!pdf.getAuthors().contains("boykev")) {
			 Bukkit.broadcastMessage(ChatColor.RED + "Je hebt lopen kloten met de plugin.yml! Je mag deze plugin niet aanpassen, dit is een overtreding van de TOS, Je plugin is hierbij geblokeerd");
			 Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Je hebt lopen kloten met de plugin.yml! Je mag deze plugin niet aanpassen, dit is een overtreding van de TOS, Je plugin is hierbij geblokeerd");
			 this.getPluginLoader().disablePlugin(this);
			 Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		if(!pdf.getName().contains("Deurbel")) {
			 Bukkit.broadcastMessage(ChatColor.RED + "Je hebt lopen kloten met de plugin.yml! Je mag deze plugin niet aanpassen, dit is een overtreding van de TOS, Je plugin is hierbij geblokeerd");
			 Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Je hebt lopen kloten met de plugin.yml! Je mag deze plugin niet aanpassen, dit is een overtreding van de TOS, Je plugin is hierbij geblokeerd");
			 this.getPluginLoader().disablePlugin(this);
			 Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		
		
		
		PluginManager pm = Bukkit.getPluginManager();
		cm = new ConfigManager(this);
		cm.LoadDefaults();
		
		if(cm.getConfig().getString("key").equals("-")) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "The plugin is setting things up please lay back.....");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                	Bukkit.broadcastMessage(ChatColor.YELLOW + "Everything is fine, have fun using Deurbel plugin :)");
                }
            }, 100);
	    	int serverport = Bukkit.getServer().getPort();
	    	String plname = "DeurbelPlugin";
	    	String bukkitip = Bukkit.getServer().getIp();
	    	try {
	    		URL url = new URL("http://api.boykevanvugt.nl/keymanager.php?type=create&version=1&plname=" + plname + "&serverport=" + serverport);
	            URLConnection connection = url.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String text = in.readLine();
	            String licfinal = text.replace(" ", "");
	            cm.getConfig().set("key", licfinal);
	            cm.save();
	            in.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		if(!cm.getConfig().getString("key").equals("-")) {
	    	String key = cm.getConfig().getString("key");
	    	try {
	            URL url = new URL("http://api.boykevanvugt.nl/keymanager.php?type=read&key=" + key);
	            URLConnection connection = url.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String text = in.readLine();
	            String licfinal = text.replace(" ", "");
	            cm.getConfig().set("key", licfinal);
	            if(licfinal.equals("valid")) {
	            	this.Status = "Valid";
	            	in.close();
	            	
	            	
	            	//Initialize Commands and Events
	            	System.out.println(ChatColor.GREEN + "Deurbel plugin opgestart!");
	        		
	        		pm.registerEvents(new SignCreate(this), this);
	        		pm.registerEvents(new SignClick(this), this);
	        		sc.makeItems();
	    			
	    			return;
	            }
	            if(licfinal.equals("abuse")) {
	            	Bukkit.broadcastMessage(ChatColor.YELLOW + "Deze server abused de Deurbel plugin!");
	            	in.close();
	            	pm.registerEvents(new JoinEvent(this), this);
	            	this.Status = "Abuse";
	            	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	                    @Override
	                    public void run() {
	                    	Bukkit.broadcastMessage(ChatColor.DARK_RED+ "Je maakt misbruik van de Deurbel plugin, neem contact op met de developer.  U are abusing the WhitehouseAccess plugin, please contact the developer");
	                    }
	                }, 0, 1800);
	            	return;
	            }
	            if(licfinal.equals("edit")) {
	            	Bukkit.broadcastMessage(ChatColor.YELLOW + "Deze server abused de Deurbel plugin door edits te maken!");
	            	in.close();
	            	pm.registerEvents(new JoinEvent(this), this);
	            	this.Status = "Edit";
	            	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	                    @Override
	                    public void run() {
	                    	Bukkit.broadcastMessage(ChatColor.DARK_RED + "Je hebt wijzigingen aangebracht in de Deurbel plugin, dit is niet toegestaan neem contact op met de developer.");
	                    }
	                }, 0, 1800);
	            	return;
	            }
	            else { 
	            	Bukkit.getServer().getPluginManager().disablePlugin(this);
		            Log.info(ChatColor.DARK_PURPLE + "Licentie FAILD" + licfinal);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.info(ChatColor.DARK_PURPLE + "Licentie FAILD");
	        }
		}
		
		
		    
		    
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("deurbel")) {
			Player p = (Player) sender;
			if(args.length != 1 ) {
				p.sendMessage(ChatColor.GOLD + "Deurbel is een plugin gemaakt door boykev");
				p.sendMessage(ChatColor.GOLD + "© Namaken of Verkopen niet toegestaan");
				PluginDescriptionFile pdf = this.getDescription();
				p.sendMessage(ChatColor.GREEN + "Plugin Versie: " + ChatColor.GRAY + pdf.getVersion());
				return false;
			}
		}
		
		
		return false;
	}
	
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-------[Deurbel Plugin]------");
    	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Plugin wordt uitgeschakeld");
    	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Made with <3 by Fire-Development (boykev)");
    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-----------------------------------");
	}
    
	
}
