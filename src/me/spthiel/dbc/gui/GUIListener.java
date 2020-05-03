package me.spthiel.dbc.commands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.spthiel.dbc.Main;

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
		Main.plugin.getLogger().info(event.getSlot() + " " + event.getInventory().getType().name());
		Player p = (Player)event.getWhoClicked();
		GUIHandler h = GUIHandler.getHandler(p);
		if(h != null) {
			h.processClick(p, event.getSlot());
			event.setCancelled(true);
		}
	}
}
