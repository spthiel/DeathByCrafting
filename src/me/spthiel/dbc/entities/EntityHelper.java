package me.spthiel.dbc.entities;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityHelper {
	
	public static void spawnItemMonster(Location spawnLoc, ItemStack item, int health) {
		
		Skeleton skeleton = (Skeleton) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.SKELETON);
		
		skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999980, 1, false, false));
		skeleton.setSilent(true);
		skeleton.setCanPickupItems(false);
		
		skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
		skeleton.setHealth(health);
		
		switch (ItemType.get(item)) {
			
			case ITEM:
				skeleton.getEquipment().setItemInMainHand(item);
				break;
			case BLOCK:
			case HELMET:
				skeleton.getEquipment().setHelmet(item);
				break;
			case CHESTPLATE:
				skeleton.getEquipment().setChestplate(item);
				break;
			case LEGGINGS:
				skeleton.getEquipment().setLeggings(item);
				break;
			case BOOTS:
				skeleton.getEquipment().setBoots(item);
				break;
		}
		
		skeleton.getEquipment().setItemInMainHand(item);
		skeleton.getEquipment().setItemInOffHand(item);
		
		String name = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", " ").toLowerCase();
		skeleton.setCustomName(name);
		skeleton.addScoreboardTag("LIVING_ITEM");
		
		spawnArmorStandAsNametag(skeleton, name);
	}
	
	private static void spawnArmorStandAsNametag(LivingEntity vehicle, String name) {
	
		Location spawnLoc = vehicle.getLocation();
		name = "" + ChatColor.GOLD + ChatColor.BOLD + name;
		
		ArmorStand nametag = (ArmorStand) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ARMOR_STAND);
		nametag.setVisible(false);
		nametag.setAI(false);
		nametag.setInvulnerable(true);
		nametag.setArms(false);
		nametag.setBasePlate(false);
		nametag.setCollidable(false);
		nametag.setGravity(false);
		nametag.setMarker(true);
		nametag.setCustomName(name);
		nametag.setCustomNameVisible(true);
		
		vehicle.addPassenger(nametag);
	}
	
}
