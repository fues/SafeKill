/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fues_ele.spigot.safe_kill.events;

import fues_ele.spigot.safe_kill.SafeKill;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

/**
 *
 * @author fues
 */
public class SKListener implements Listener {

    private final SafeKill plugin;

    public SKListener(SafeKill plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String cmd = event.getMessage();
        Matcher m = this.makeMatcher(cmd, "unsafekill");
        if (m.find()) {
            String newCmd = m.replaceFirst("kill");
            this.plugin.log(newCmd);
            event.setMessage(newCmd);
        } else {
            boolean cancel = this.checkUnsafeKill(cmd);
            if (cancel) {
                event.getPlayer().sendMessage(ChatColor.RED + "[" + this.plugin.getName() + "] This command is unsafe!");
                this.plugin.log("block kill command \"" + cmd + "\"");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ServerCommand(ServerCommandEvent event) {
        String cmd = event.getCommand();
        Matcher m = this.makeMatcher(cmd, "unsafekill");
        if (m.find()) {
            String newCmd = m.replaceFirst("kill");
            this.plugin.log(newCmd);
            event.setCommand(newCmd);
        } else {
            boolean cancel = this.checkUnsafeKill(cmd);
            if (cancel) {
                this.plugin.log("block kill command \"" + cmd + "\"");
                event.setCancelled(true);
            }
        }
    }

    private boolean checkUnsafeKill(String cmd) {
        if (this.makeMatcher(cmd, "kill\\s@e$").find()) {
            return true;
        }
        Matcher m = this.makeMatcher(cmd, "kill\\s@e\\[(.*)\\]$");
        if (m.find()) {
            String selector = m.group(1);
            if (this.makeMatcher(selector, "sort\\s?=").find() && this.makeMatcher(selector, "limit\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "distance\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "tag\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "name\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "dx\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "dy\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "dz\\s?=").find()) {
                return false;
            }
            if (this.makeMatcher(selector, "score\\s?=").find()) {
                return false;
            }
            return true;
        }
        return false;
    }

    private Matcher makeMatcher(String cmd, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(cmd);
    }
}
