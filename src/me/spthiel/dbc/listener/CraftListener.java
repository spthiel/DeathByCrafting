package me.spthiel.dbc.listener;

import java.util.SplittableRandom;

import me.spthiel.dbc.entities.EntityHelper;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftListener implements Listener {
	
	SplittableRandom rand = new SplittableRandom();
	
	public CraftListener() {
	
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		
		if (!e.getAction().equals(InventoryAction.NOTHING)) {
			if (e.getAction().equals(InventoryAction.PICKUP_ALL) || e
					.getAction()
					.equals(InventoryAction.PICKUP_HALF) || e.getAction().equals(InventoryAction.HOTBAR_SWAP) || e
					.getAction()
					.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
				boolean     shiftClick = e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY);
				Location    bLoc       = e.getInventory().getLocation();
				ItemStack[] materials  = e.getInventory().getContents();
				int         minimum    = 1;
				short       i;
				if (shiftClick) {
					minimum = 64;
					
					for (i = 0; i < materials.length ; ++i) {
						if (!materials[i].getType().equals(Material.AIR)) {
							if (materials[i].getAmount() < minimum) {
							}
							
							minimum = materials[i].getAmount();
						}
					}
				}
				
				for (i = 0; i < e.getInventory().getResult().getAmount() * minimum ; ++i) {
					Location l      = new Location(
							bLoc.getWorld(),
							(double) bLoc.getBlockX() + this.rand.nextDouble(),
							(double) (bLoc.getBlockY() + 1),
							(double) bLoc.getBlockZ() + this.rand.nextDouble()
					);
					
					
					EntityHelper.spawnItemMonster(l, e.getInventory().getResult(), 6);
//					Zombie   zombie = (Zombie) bLoc.getWorld().spawnEntity(l, EntityType.ZOMBIE);
//					zombie.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999980, 1, false, false));
//					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 19999980, 1, false, false));
//					zombie.setBaby(true);
//					zombie.setSilent(true);
//					zombie.setCanPickupItems(false);
//					zombie.setHealth(10.0D);
//					zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10.0D);
//					ItemStack newItemStack = e.getInventory().getResult().clone();
//					newItemStack.setAmount(1);
//					zombie.getEquipment().setHelmet(newItemStack);
//					zombie.getEquipment().setChestplate(newItemStack);
//					zombie.getEquipment().setLeggings(newItemStack);
//					zombie.getEquipment().setBoots(newItemStack);
//					zombie.getEquipment().setItemInMainHand(newItemStack);
//					zombie.getEquipment().setItemInOffHand(newItemStack);
//					String name;
//					if (e.getInventory().getResult().getItemMeta().hasDisplayName()) {
//						name = e.getInventory().getResult().getItemMeta().getDisplayName();
//					} else {
//						name = e.getInventory().getResult().getType().name().replace("_", " ").toLowerCase();
//					}
//
//					name = "" + ChatColor.GOLD + ChatColor.BOLD + name;
//					ArmorStand nametag = (ArmorStand) bLoc.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
//					nametag.setVisible(false);
//					nametag.setAI(false);
//					nametag.setInvulnerable(true);
//					nametag.setArms(false);
//					nametag.setBasePlate(false);
//					nametag.setCollidable(false);
//					nametag.setGravity(false);
//					nametag.setMarker(true);
//					nametag.setCustomName(name);
//					nametag.setCustomNameVisible(true);
//					zombie.addPassenger(nametag);
//					zombie.setCustomName(name);
//					zombie.addScoreboardTag("LIVING_ITEM");
				}
				
				for (i = 0; i < materials.length ; ++i) {
					if (materials[i].getAmount() > 0) {
						if (shiftClick) {
							materials[i].setAmount(0);
						} else {
							materials[i].setAmount(materials[i].getAmount() - 1);
						}
					}
				}
				
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		
		if (e.getEntityType().equals(EntityType.SKELETON) && e.getEntity().getScoreboardTags().contains("LIVING_ITEM")) {
			e.getEntity().getPassengers().forEach((p) -> {
				p.remove();
			});
			e.getDrops().clear();
			e.getDrops().add(e.getEntity().getEquipment().getHelmet());
		}
		
	}
}
