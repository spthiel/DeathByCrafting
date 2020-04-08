package me.spthiel.dbc;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class Stats {
	
	private float health;
	private float damage;
	
	public Stats(ConfigurationSection section) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object o = section.get(field.getName());
			try {
				field.set(o, this);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public float getHealth() {
		
		return health;
	}
	
	public float getDamage() {
		
		return damage;
	}
}
