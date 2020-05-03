package me.spthiel.dbc;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.spthiel.dbc.commands.DBCCommands;
import me.spthiel.dbc.gui.GUIListener;
import me.spthiel.dbc.config.Config;
import me.spthiel.dbc.listener.CraftListener;
import me.spthiel.dbc.config.Stats;

import java.util.Arrays;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	public void onEnable() {
		
		plugin = this;
		saveDefaultConfig();
		reload();
		this.getServer().getPluginManager().registerEvents(new CraftListener(), this);
		this.getServer().getPluginManager().registerEvents(new GUIListener(), this);
		getCommand("dbc").setExecutor(new DBCCommands());
	}
	
	@SuppressWarnings("WeakerAccess")
	public void reload() {
		
		List<Integer> ints = null;
		
		FileConfiguration    config  = getConfig();
		ConfigurationSection section = config.getConfigurationSection("materials");
		if (section == null) {
			getLogger().severe("Missing 'materials' section in config");
			return;
		}
		
		Config.statTypes = new HashMap<>();
		Config.types = new HashMap<>();
		Config.defaultStats = Stats.instance;
		
		Set<String> keys = section.getKeys(false);
		for (String key : keys) {
			ConfigurationSection matSection = section.getConfigurationSection(key);
			Stats                stats      = new Stats(matSection);
			Config.statTypes.put(key, stats);
			if (key.equalsIgnoreCase("default")) {
				Config.defaultStats = stats;
				continue;
			}
			
			//noinspection ConstantConditions
			if (matSection.isList("appliesTo")) {
				List<String> materials = matSection.getStringList("appliesTo");
				materials.stream()
						 .map(Material :: matchMaterial)
						 .filter(Objects :: nonNull)
						 .forEach(material -> {
							 Config.types.put(material, new Stats(matSection));
						 });
			} else if (matSection.isString("appliesTo")) {
				@SuppressWarnings("ConstantConditions")
				Material mat = Material.matchMaterial(matSection.getString("appliesTo"));
				if (mat == null) {
					getLogger().severe("Couldn't find a material for " + key + ". Check your config.");
				} else {
					Config.types.put(mat, stats);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		Arrays
				.stream(Material.values())
				.map(Material :: name)
				.map(String :: toLowerCase)
				.filter(name -> !name.startsWith("legacy"))
				.forEach(System.out :: println);
		
	}
	
}