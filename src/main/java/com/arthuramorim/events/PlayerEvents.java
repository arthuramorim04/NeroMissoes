package com.arthuramorim.events;

import com.arthuramorim.NeroMissoes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    private NeroMissoes plugin;

    public PlayerEvents(NeroMissoes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void joinPlayer(PlayerJoinEvent e) {
        this.plugin.getPlugin().getMissionPlayerController().loadPlayer(e.getPlayer());
    }

}
