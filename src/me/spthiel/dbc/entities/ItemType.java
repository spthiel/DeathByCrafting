package me.spthiel.dbc.entities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemType {
	
	BLOCK, ITEM, HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	
	public static ItemType get(ItemStack itemStack) {
		
		Material type = itemStack.getType();
		
		if (type.isBlock()) {
			return BLOCK;
		}
		
		String typeName = type.name();
		
		if (typeName.endsWith("HELMET")) {
			return HELMET;
		}
		
		if (typeName.endsWith("CHESTPLATE")) {
			return CHESTPLATE;
		}
		
		if (typeName.endsWith("LEGGINS")) {
			return LEGGINGS;
		}
		
		if (typeName.endsWith("BOOTS")) {
			return BOOTS;
		}
		
		return ITEM;
	}
}

