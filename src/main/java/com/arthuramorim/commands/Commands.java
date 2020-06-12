package com.arthuramorim.commands;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.utils.utils.TextUtil;
import org.bukkit.Bukkit;
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

        if(command.getName().equalsIgnoreCase("missoesreload")){
            if(p.hasPermission("neromissoes.admin")){
                plugin.getHashMission().clear();
                plugin.getMissoes().reload();
                plugin.getMissionManager().loadMission();
                if(p instanceof Player){
                    p.sendMessage(TextUtil.color("&e[NeroMissoes] &aconfigurações recarregadas com sucesso!"));
                }else{
                    Bukkit.getConsoleSender().sendMessage(TextUtil.color("&e[NeroMissoes] &aconfigurações recarregadas com sucesso!"));
                }
            }
            return false;
        }
        return false;
    }
}
