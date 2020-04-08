package me.spthiel.dbc;

import org.bukkit.plugin.java.JavaPlugin;

import me.spthiel.dbc.listener.CraftListener;

public class Main extends JavaPlugin {
    
    public static Main main;
    
    public void onEnable() {
        main = this;
        this.getServer().getPluginManager().registerEvents(new CraftListener(), this);
    }
    
    
}
