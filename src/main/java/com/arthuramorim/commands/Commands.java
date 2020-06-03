package com.arthuramorim.commands;

import com.arthuramorim.NeroMissoes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private NeroMissoes plugin;

    public Commands(NeroMissoes plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        if(command.getName().equalsIgnoreCase("missoes")){
            plugin.getMissionMenus().openMenuMission(p);
            return false;
        }

        if(command.getName().equalsIgnoreCase("teste")){
            p.sendMessage(plugin.getHashPlayer().toString());
            p.sendMessage("\n\n");
            p.sendMessage(plugin.getHashMission().toString());
            p.sendMessage(plugin.getMissoes().getConfigFile().getConfigurationSection("mission").getString("1.missionName"));
            return false;
        }

        if(command.getName().equalsIgnoreCase("missoesreload")){
            if(p.hasPermission("neromissoes.admin")){
                plugin.getHashMission().clear();
                plugin.getMissoes().reload();
                plugin.getMissionManager().loadMission();
            }
            return false;
        }
        return false;
    }
}
