package com.arthuramorim.events;

import com.arthuramorim.NeroMissoes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsPlayer implements Listener {

    private NeroMissoes plugin;

    public EventsPlayer(NeroMissoes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        if (e.getPlayer().hasPlayedBefore()) {
            plugin.getMissionPlayerController().loadPlayer(e.getPlayer());
        } else {
            plugin.getMissionPlayerController().registerNewPlayer(e.getPlayer());
        }

    }

    @EventHandler
    public void quitPlayer(PlayerQuitEvent e) {
        plugin.getMissionPlayerController().savePlayer(plugin.getHashPlayer().get(e.getPlayer().getName()));
    }
}
