package me.spthiel.dbc.listener;

import java.util.SplittableRandom;

import me.spthiel.dbc.config.Config;
import me.spthiel.dbc.entities.EntityHelper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import me.spthiel.dbc.Main;

public class CraftListener implements Listener {
	
	private SplittableRandom rand = new SplittableRandom();
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		
		if (!e.getAction().equals(InventoryAction.NOTHING)) {
			if (e.getAction().equals(InventoryAction.PICKUP_ALL) ||
					e.getAction().equals(InventoryAction.PICKUP_HALF) ||
					e.getAction().equals(InventoryAction.HOTBAR_SWAP) ||
					e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
				
				e.setCancelled(true);
				Location bLoc = e.getInventory().getLocation();
				
				int itemsChecked      = 0;
				int possibleCreations = 1;
				for (ItemStack item : e.getInventory().getMatrix()) {
					if (item != null && !item.getType().equals(Material.AIR)) {
						if (itemsChecked == 0) {
							possibleCreations = item.getAmount();
						} else {
							possibleCreations = Math.min(possibleCreations, item.getAmount());
						}
						itemsChecked++;
					}
				}
				for (int i = 0 ; i < possibleCreations ; i++) {
					Location location = new Location(
							bLoc.getWorld(),
							(double) bLoc.getBlockX() + this.rand.nextDouble(),
							(double) (bLoc.getBlockY() + 1),
							(double) bLoc.getBlockZ() + this.rand.nextDouble()
					);
					
					ItemStack result = e.getRecipe().getResult();
					EntityHelper.spawnItemMonster(
							(Player) e.getWhoClicked(),
							location,
							result,
							Config.types.getOrDefault(result.getType(), Config.defaultStats)
												 );
				}
				cleanupCrafting(possibleCreations, e.getInventory());
				
			}
		}
	}
	
	private void cleanupCrafting(int possibleCreations, CraftingInventory inv) {
		
		ItemStack[] matrix = inv.getMatrix();
		inv.setResult(new ItemStack(Material.AIR));
		for (int i = 0 ; i < matrix.length ; i++) {
			ItemStack item = matrix[i];
			if (item != null && !item.getType().equals(Material.AIR)) {
				int newamount = item.getAmount() - possibleCreations;
				if (newamount == 0) {
					matrix[i] = new ItemStack(Material.AIR);
				}
				item.setAmount(newamount);
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		
		if (e.getEntity().getScoreboardTags().contains("LIVING_ITEM")) {
			e.getEntity().getPassengers().forEach(Entity :: remove);
			e.getDrops().clear();
			e.getDrops().add(getDrops(e.getEntity()));
		}
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		World    world = e.getEntity().getWorld();
		Location loc   = e.getEntity().getLocation();
		world.getEntities()
			 .stream()
			 .filter(entity -> entity instanceof LivingEntity)
			 .map(entity -> (LivingEntity) entity)
			 .filter(entity -> entity.getScoreboardTags().contains("LIVING_ITEM"))
			 .filter(entity -> entity.getScoreboardTags().contains(e.getEntity().getUniqueId().toString()))
			 .forEach(entity -> {
				 ItemStack drops = getDrops(entity);
				 world.dropItem(loc, drops);
				 entity.remove();
			 });
	}
	
	private ItemStack getDrops(LivingEntity entity) {
		
		EntityEquipment equipment = entity.getEquipment();
		if (equipment != null) {
			if (isItem(equipment.getHelmet())) {
				return equipment.getHelmet();
			}
			if (isItem(equipment.getChestplate())) {
				return equipment.getChestplate();
			}
			if (isItem(equipment.getLeggings())) {
				return equipment.getLeggings();
			}
			if (isItem(equipment.getBoots())) {
				return equipment.getBoots();
			}
			if (isItem(equipment.getItemInMainHand())) {
				return equipment.getItemInMainHand();
			}
		}
		Main.plugin
				.getLogger()
				.severe("Something went wrong trying to get the item of a living item " + entity.getCustomName());
		return new ItemStack(Material.AIR);
	}
	
	private boolean isItem(ItemStack stack) {
		
		return stack != null && stack.getAmount() != 0 && !stack.getType().isAir();
	}
}
