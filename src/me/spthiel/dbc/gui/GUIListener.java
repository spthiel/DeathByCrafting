package me.spthiel.dbc.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIListener implements Listener {
	
	@EventHandler
	public void onCloseGui(InventoryCloseEvent event) {
		
		Player p = (Player)event.getPlayer();
		GUIHandler h = GUIHandler.getHandler(p);
		if(h != null) {
			h.close();
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		GUIHandler h = GUIHandler.getHandler(event.getWhoClicked());
		if(h != null) {
			h.onClick(event.getSlot(), event);
			event.setCancelled(true);
		}
	}
}
