package me.spthiel.dbc.commands;

import org.bukkit.entity.Player;

import me.spthiel.dbc.gui.GUIHandler;

public class DBCUI {
	
	private GUIHandler handler;
	
	public DBCUI(Player p) {
		
		handler = new GUIHandler(p, "DBC - Init");
		
		
	}
	
}
