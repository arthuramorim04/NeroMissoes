package com.arthuramorim.task;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Tasks extends BukkitRunnable {

    private NeroMissoes plugin;

    public Tasks(NeroMissoes plugin){
        this.plugin = plugin;

    }

    @Override
    public void run() {
        saveAll();
    }

    public void saveAll(){
        if(Bukkit.getOnlinePlayers().isEmpty())return;

        for(Player p : Bukkit.getOnlinePlayers()){
            EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());
            plugin.getMpc().savePlayer(entityPlayer);
        }
    }
}
