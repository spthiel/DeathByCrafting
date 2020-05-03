package me.spthiel.dbc.config;

import org.bukkit.ChatColor;

public class ModifiableStats extends Stats {
	
	public ModifiableStats() {
		super(10, 3, 0, 0.23, 1, 2, 0, ChatColor.WHITE);
	}
	
	public void setColor(ChatColor color) {
		
		this.color = color;
	}
}
