/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fues_ele.spigot.safe_kill;

import fues_ele.spigot.safe_kill.events.SKListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author fues
 */
public class SafeKill extends JavaPlugin{
    
    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new SKListener(this), this);
    }

    public void log(String str) {
        this.getLogger().info(str);
    }

    public void warn(String str) {
        this.getLogger().warning(str);
    }
}
