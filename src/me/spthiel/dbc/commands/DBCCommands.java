package me.spthiel.dbc.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.spthiel.dbc.Main;

public class DBCCommands implements CommandExecutor {
	
	@SuppressWarnings("NullableProblems")
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		
		if(args.length == 0) {
			return false;
		}
		
		boolean init = args[0].equalsIgnoreCase("init");
		if(init && commandSender instanceof ConsoleCommandSender) {
			commandSender.sendMessage("Cannot use that command from the console");
			return false;
		}
		
		Player p = (Player)commandSender;
		
		
		if(init) {
			if(!p.hasPermission("dbc.init") && !p.isOp()) {
				noPerms(p);
				return true;
			}
			new InitializeUI(p);
			return true;
		} else if(args[0].equalsIgnoreCase("reload")) {
			if (!p.hasPermission("dbc.reload") && !p.isOp()) {
				noPerms(p);
				return true;
			}
			Main.plugin.reload();
			commandSender.sendMessage("Config reloaded");
		} else if(args[0].equalsIgnoreCase("test")) {
			Inventory inv = Bukkit.createInventory(p, InventoryType.ANVIL, "Test");
			inv.setItem(0, new ItemStack(Material.PAPER));
			p.openInventory(inv);
		} else {
			return false;
		}
		return true;
	}
	
	private void noPerms(Player p) {
		p.sendMessage(ChatColor.RED + "You don't have permissions to use this command");
	}
}
