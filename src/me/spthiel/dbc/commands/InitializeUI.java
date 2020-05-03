package me.spthiel.dbc.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.spthiel.dbc.config.Config;
import me.spthiel.dbc.config.ModifiableStats;
import me.spthiel.dbc.config.Stats;
import me.spthiel.dbc.gui.GUIHandler;

public class InitializeUI {
	
	private GUIHandler handler;
	
	private static int progress = 0;
	private static Material[] materials = Arrays
			.stream(Material.values()).filter(material -> !material.name().startsWith("LEGACY")).toArray(Material[]::new);
	
	public InitializeUI(Player p) {
		
		handler = new GUIHandler(p, "DBC - Init");
		buildStartUI();
		
	}
	
	private void buildStartUI() {
		handler.setSize(3);
		handler.setItem(11, Material.RED_DYE, "Cancel")
			   .then(event -> handler.close());
		handler.setItem(15, Material.LIME_DYE, "Start")
			   .then(ignored -> buildFullUI());
	}
	
	private void buildFullUI() {
		handler.setSize((Config.statTypes.size()+2)/9+3);
		handler.clearInventory();
		Material m;
		do {
			if(materials.length < progress-1) {
				return;
			}
			m = materials[++progress];
		} while((m == null || m.equals(Material.AIR)));
		ItemStack          stack = new ItemStack(m);
		ItemMeta           meta  = stack.getItemMeta();
		LinkedList<String> lore  = new LinkedList<>();
		lore.add(ChatColor.YELLOW + "Progress: " + progress + "/" + materials.length);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		handler.setItem(4, stack);
		placeGroups();
		handler.setItem(-1, Material.MAGENTA_DYE, "Add group");
	}
	
	private void placeGroups() {
		int startSlot = 18;
		for(String name : Config.statTypes.keySet()) {
			Stats stats = Config.statTypes.get(name);
			handler.setItem(startSlot++, colorToGlass(stats.getColor()), stats.getColor() + name, meta -> {
				meta.setLore(genLore(stats));
			});
		}
	}
	
	private List<String> genLore(Stats stats) {
		
		LinkedList<String> out = new LinkedList<>();
		stats.forEach(strings -> out.add(ChatColor.GREEN + strings[0] + ": " + ChatColor.GRAY + strings[1]));
		return out;
	}
	
	private static final String[] PREDEFINED = {
			"Basic",
			"Normal",
			"Tougher",
			"Advanced",
			"Tougher",
			"Toughest",
			"Hard",
			"Expert",
			"Mega",
			"Ultra",
			"Super",
			"Hyper"
	};
	
	private static final String          GENERIC = "Group %d";
	private              ModifiableStats newStats;
	private              String          newGroupName;
	
	private void buildAddGroupUI() {
		
		handler.setSize(3);
		
		int firstIdx = 0;
		while(firstIdx < PREDEFINED.length && Config.statTypes.containsKey(PREDEFINED[firstIdx])) {
			firstIdx++;
		}
		if(firstIdx == PREDEFINED.length) {
			firstIdx = -1;
		}
		
		int firstGeneric = findFirstGeneric();
		String first = firstIdx == -1 ? String.format(GENERIC, firstGeneric) : PREDEFINED[firstIdx];
		
		handler.setItem(0, Material.END_CRYSTAL, "Name", meta -> meta.setLore(toList(newGroupName = first)));
		buildNameRow(firstIdx, firstGeneric);
	}
	
	private void buildNameRow(int firstIdx, int firstGeneric) {
		
		for(int i = 0; i < 8; i++) {
			String current = firstIdx == -1 || firstIdx >= PREDEFINED.length ? String.format(GENERIC, firstGeneric++) : PREDEFINED[firstIdx++];
			handler.setItem(0, current.equalsIgnoreCase(newGroupName) ? Material.GOLD_BLOCK : Material.IRON_BLOCK, current, meta -> meta.setLore(toList(current)));
		}
	}
	
	private List<String> toList(String str) {
		List<String> out = new ArrayList<>();
		out.add(str);
		return out;
	}
	
	private int findFirstGeneric() {
		int idx = 1;
		while(Config.statTypes.containsKey(String.format(GENERIC, idx))) {
			idx++;
		}
		return idx;
	}
	
	private Material colorToGlass(ChatColor color) {
		switch (color) {
			case AQUA:
				return Material.LIGHT_BLUE_STAINED_GLASS;
			case GOLD:
				return Material.ORANGE_STAINED_GLASS;
			case RED:
				return Material.REDSTONE_BLOCK;
			case BLUE:
				return Material.LAPIS_BLOCK;
			case GRAY:
				return Material.LIGHT_GRAY_STAINED_GLASS;
			case BLACK:
				return Material.BLACK_STAINED_GLASS;
			case GREEN:
				return Material.LIME_STAINED_GLASS;
			case YELLOW:
				return Material.YELLOW_STAINED_GLASS;
			case DARK_RED:
				return Material.RED_STAINED_GLASS;
			case DARK_AQUA:
				return Material.CYAN_STAINED_GLASS;
			case DARK_BLUE:
				return Material.BLUE_STAINED_GLASS;
			case DARK_GRAY:
				return Material.GRAY_STAINED_GLASS;
			case DARK_GREEN:
				return Material.GREEN_STAINED_GLASS;
			case DARK_PURPLE:
				return Material.PURPLE_STAINED_GLASS;
			case LIGHT_PURPLE:
				return Material.PINK_STAINED_GLASS;
			default:
				return Material.WHITE_STAINED_GLASS;
		}
	}
}
