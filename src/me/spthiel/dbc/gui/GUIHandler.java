package me.spthiel.dbc.gui;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIHandler {
	
	private static HashMap<UUID, GUIHandler> guiHandlers = new HashMap<>();
	
	public static GUIHandler getHandler(HumanEntity player) {
		return getHandler(player.getUniqueId());
	}
	
	public static GUIHandler getHandler(UUID player) {
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
			chains.clear();
			inventory = Bukkit.createInventory(p, size*9, name);
			p.closeInventory();
			p.openInventory(inventory);
			this.size = size;
		} else {
			clearInventory();
		}
		return this;
	}
	
	public void close() {
		getPlayer().closeInventory();
		chains.clear();
		guiHandlers.remove(player);
	}
	
	public int getLastSlot() {
		return inventory.getSize()-1;
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
	
	public Chain<InventoryClickEvent> setItem(int slot, Material material, String name, Consumer<ItemMeta> customMeta) {
		return setItem(slot, createItem(material, name, customMeta));
	}
	
	public Chain<InventoryClickEvent> setItem(int slot, Material material, String name) {
		return setItem(slot, createItem(material, name));
	}
	
	public Chain<InventoryClickEvent> setItem(int slot, ItemStack itemStack) {
		if(slot < 0) {
			slot = inventory.getSize()+slot;
		}
		inventory.setItem(slot, itemStack);
		Chain<InventoryClickEvent> chain = new Chain<>();
		chains.put(slot, chain);
		return chain;
	}
	
	public ItemStack createItem(Material material, String name, Consumer<ItemMeta> customMeta) {
		
		ItemStack out  = new ItemStack(material);
		ItemMeta  meta = out.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + name);
		if(customMeta != null) {
			customMeta.accept(meta);
		}
		out.setItemMeta(meta);
		return out;
	}
	
	public ItemStack createItem(Material material, String name) {
		
		return createItem(material, name, null);
	}
	
}