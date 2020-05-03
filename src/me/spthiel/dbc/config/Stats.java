package me.spthiel.dbc.stats;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import me.spthiel.dbc.Main;
import me.spthiel.dbc.config.Config;

public class Stats {
	
	public static Stats instance = new Stats(20, 3, 0, 0.23, 1, 2, 0, ChatColor.WHITE);
	
	private double health = 0;
	private double damage = 0;
	private double knockbackResistance = 0;
	private double speed = 0;
	private double attackSpeed = 0;
	private double armor = 2;
	private double armorToughness = 0;
	private ChatColor color;
	
	Stats(double health, double damage, double knockbackResistance, double speed, double attackSpeed, double armor, double armorToughness, ChatColor color) {
		
		this.health = health;
		this.damage = damage;
		this.knockbackResistance = knockbackResistance;
		this.speed = speed;
		this.attackSpeed = attackSpeed;
		this.armor = armor;
		this.armorToughness = armorToughness;
		this.color = color;
	}
	
	public Stats(ConfigurationSection section) {

		Field[] fields = this.getClass().getDeclaredFields();

		for (Field field : fields) {
			Object o;
			String name = field.getName();
			Class c = field.getType();
			try {
				if (c.equals(Long.TYPE)) {
					field.set(this, section.getLong(name));
				} else if (c.equals(Integer.TYPE)) {
					field.set(this, section.getInt(name));
				} else if (c.equals(Short.TYPE)) {
					field.set(this, (short)section.getInt(name));
				} else if (c.equals(Byte.TYPE)) {
					field.set(this, (byte)section.getInt(name));
				} else if (c.equals(String.class)) {
					field.set(this, section.getString(name));
				} else if (c.equals(Double.TYPE)) {
					field.set(this, section.getDouble(name));
				} else if (c.equals(Float.TYPE)) {
					field.set(this, (float)section.getDouble(name));
				} else if (c.equals(Boolean.TYPE)) {
					field.set(this, section.getBoolean(name));
				} else if (c.equals(ChatColor.class)) {
					String color = section.getString(name);
					if(color == null) {
						continue;
					}
					ChatColor chatColor = null;
					try {
						chatColor = ChatColor.valueOf(color);
					} catch(IllegalArgumentException e) {
						if(color.length() <= 2) {
							chatColor = ChatColor.getByChar(color.charAt(color.length()-1));
						}
						if(chatColor == null) {
							chatColor = ChatColor.valueOf(color.toUpperCase());
						}
					}
					System.out.println("Set: " + chatColor.name());
					field.set(this, chatColor);
				} else {
					throw new RuntimeException(c.getName());
				}
			} catch (IllegalAccessException e){
				e.printStackTrace();
			} catch (IllegalArgumentException | ClassCastException e) {
				Main.plugin.getLogger().severe("Invalid format for " + name + ": " + section.get(name) + " for " + c.getName());
			}
		}
	}
	
	public double getHealth() {
		
		return health <= 0 ? (Config.defaultStats.health <= 0 ? instance.health : Config.defaultStats.health) : health;
	}
	
	public double getDamage() {
		
		return damage <= 0 ? (Config.defaultStats.damage <= 0 ? instance.damage : Config.defaultStats.damage) : damage;
	}
	
	public double getKnockbackResistance() {
		
		return knockbackResistance <= 0 ? (Config.defaultStats.knockbackResistance <= 0 ? instance.knockbackResistance : Config.defaultStats.knockbackResistance) : knockbackResistance;
	}
	
	public double getSpeed() {
		
		return speed <= 0 ? (Config.defaultStats.speed <= 0 ? instance.speed : Config.defaultStats.speed) : speed;
	}
	
	public double getAttackSpeed() {
		
		return attackSpeed <= 0 ? (Config.defaultStats.attackSpeed <= 0 ? instance.attackSpeed : Config.defaultStats.attackSpeed) : attackSpeed;
	}
	
	public ChatColor getColor() {
		
		return color == null ? (Config.defaultStats.color == null ? ChatColor.WHITE : Config.defaultStats.color) : color;
	}
	
	public double getArmor() {
		
		return armor;
	}
	
	public double getArmorToughness() {
		
		return armorToughness;
	}
	
	@Override
	public String toString() {
		
		return "Stats{" +
				"health=" + health +
				", damage=" + damage +
				", knockbackResistance=" + knockbackResistance +
				", speed=" + speed +
				", attackSpeed=" + attackSpeed +
				", armor=" + armor +
				", armorToughness=" + armorToughness +
				", color=" + color.name() +
				'}';
	}
}
