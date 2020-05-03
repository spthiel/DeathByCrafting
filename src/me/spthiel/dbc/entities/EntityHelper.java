package me.spthiel.dbc.entities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import me.spthiel.dbc.config.Stats;
import me.spthiel.dbc.TeamUtils;

public class EntityHelper {
	
	public static void spawnItemMonster(Player crafter, Location spawnLoc, ItemStack item, Stats stats) {
		
		Monster monster;
		if(item.getType().equals(Material.BOW)) {
			monster = (Skeleton) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.SKELETON);
		} else {
			monster = (Monster) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.PIG_ZOMBIE);
		}
		
		monster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999980, 1, false, false));
		monster.setGlowing(true);
		monster.setSilent(true);
		monster.setCanPickupItems(false);
		
		if(monster instanceof PigZombie) {
			PigZombie pigZombie = (PigZombie)monster;
			pigZombie.setAngry(true);
			pigZombie.setBaby(true);
		}
		
		monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.getHealth());
		monster.setHealth(stats.getHealth());
		monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(stats.getDamage());
		monster.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(100);
		monster.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(stats.getKnockbackResistance());
		monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(stats.getSpeed());
		monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(stats.getAttackSpeed());
		monster.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(stats.getArmor());
		monster.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(stats.getArmorToughness());
		
		switch (ItemType.get(item)) {
			case ITEM:
			case BLOCK:
			case HELMET:
				monster.getEquipment().setHelmet(item);
				break;
			case CHESTPLATE:
				monster.getEquipment().setChestplate(item);
				break;
			case LEGGINGS:
				monster.getEquipment().setLeggings(item);
				break;
			case BOOTS:
				monster.getEquipment().setBoots(item);
				break;
		}
		monster.getEquipment().setItemInMainHand(item);
		monster.setTarget(crafter);
		
		String name = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", " ").toLowerCase();
		monster.setCustomName(name);
		monster.addScoreboardTag("LIVING_ITEM");
		monster.addScoreboardTag(crafter.getUniqueId().toString());
		
		setColor(monster, stats.getColor());
	}
	
	private static TeamUtils utils = new TeamUtils();
	
	private static void setColor(Entity entity, ChatColor color) {
		
		Team t = utils.createOrGetColorTeam(color);
		t.addEntry(entity.getUniqueId().toString());
		
	}
	
}
