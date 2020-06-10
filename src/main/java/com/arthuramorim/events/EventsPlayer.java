package com.arthuramorim.events;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.controller.MissionPlayerController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventsPlayer implements Listener {

    private NeroMissoes plugin;

    public EventsPlayer(NeroMissoes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        if (e.getPlayer().hasPlayedBefore()) {
            plugin.getMpc().loadPlayer(e.getPlayer());
        } else {
            plugin.getMpc().registerNewPlayer(e.getPlayer());
        }

    }

    @EventHandler
    public void quitPlayer(PlayerQuitEvent e) {
        plugin.getMpc().savePlayer(plugin.getHashPlayer().get(e.getPlayer().getName()));
    }
}
