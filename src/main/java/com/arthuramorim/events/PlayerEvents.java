package com.arthuramorim.events;

import com.arthuramorim.NeroMissoes;
import com.github.eokasta.core.plugin.KPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {


    @EventHandler
    public void joinPlayer(PlayerJoinEvent e) {
        NeroMissoes.getPlugin().getMpc().loadPlayer(e.getPlayer());
    }

}
