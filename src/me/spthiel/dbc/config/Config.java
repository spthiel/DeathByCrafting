package me.spthiel.dbc.config;

import java.util.HashMap;

import org.bukkit.Material;

public class Config {
	
	public static HashMap<String, Stats>   statTypes = new HashMap<>();
	public static HashMap<Material, Stats> types     = new HashMap<>();
	public static Stats                    defaultStats;
	
}
