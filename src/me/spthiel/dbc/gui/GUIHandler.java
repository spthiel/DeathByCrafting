package me.spthiel.dbc.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

class GUIHandler {
	
	private static HashMap<UUID, GUIHandler> guiHandlers = new HashMap<>();
	
	public static GUIHandler getGUIHandler(Player player) {
		return getGUIHandler(player.getUniqueId());
	}
	
	public static GUIHandler getGUIHandler(UUID player) {
		return guiHandlers.get(player);
	}
	
	private UUID player;
	private int size = 0;
	private Inventory inventory;
	private String name;
	
	private HashMap<Integer, Chain<InventoryClickEvent>> chains;
	
	public GUIHandler(Player p, String uiname) {
		this.player = p.getUniqueId();
		this.name = uiname;
		chains = new HashMap<>();
		guiHandlers.put(player, this);
	}
	
	public GUIHandler setSize(int size) {
		
		if(this.size != size) {
			Player p = getPlayer();
			inventory = Bukkit.createInventory(p, size*9, name);
			p.closeInventory();
			p.openInventory(inventory);
			this.size = size;
		}
		return this;
	}
	
	public void close() {
		getPlayer().closeInventory();
		chains.clear();;
	}
	
	public void clearInventory() {
		inventory.clear();
		chains.clear();
	}
	
	public void onClick(int slot, InventoryClickEvent event) {
		if(chains.containsKey(slot)) {
			chains.get(slot).trigger(event);
		}
	}
	
	private Player getPlayer() {
		return Bukkit.getPlayer(player);
	}
	
}