package me.spthiel.dbc;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.spthiel.dbc.listener.CraftListener;

public class Main extends JavaPlugin {
	
	private Logger logger = Logger.getLogger("[DBC]");
	public static Main main;
	
	public void onEnable() {
		
		main = this;
		saveDefaultConfig();
		reload();
		this.getServer().getPluginManager().registerEvents(new CraftListener(), this);
	}
	
	public void reload() {
		
		FileConfiguration    config  = getConfig();
		ConfigurationSection section = config.getConfigurationSection("materials");
		Set<String>          keys    = section.getKeys(false);
		for (String key : keys) {
			
			if(key.equalsIgnoreCase("default")) {
				Config.defaultStats = new Stats(section.getConfigurationSection(key));
			} else {
				Material mat = Material.matchMaterial(key);
				if(mat == null) {
					System.err.println("Couldn't find a material for " + key + ". Check your config.");
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		Arrays.stream(Material.values()).map(Material::name).map(String::toLowerCase).filter(name -> !name.startsWith("legacy")).forEach(System.out::println);
		
	}
	
}
