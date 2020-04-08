package me.spthiel.dbc;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class Stats {
	
	private int health;
	private int damage;
	
	public Stats(ConfigurationSection section) {
//
//		Field[] fields = this.getClass().getDeclaredFields();
//
//		for (Field field : fields) {
//			Object o = section.get(field.getName());
//			try {
//				field.set(o, this);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public int getHealth() {
		
		return health;
	}
	
	public int getDamage() {
		
		return damage;
	}
}
