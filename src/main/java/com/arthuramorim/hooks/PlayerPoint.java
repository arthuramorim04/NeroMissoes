package com.arthuramorim.hooks;

import com.arthuramorim.NeroMissoes;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.plugin.Plugin;

/**/
public class PlayerPoint {

    private PlayerPoints playerPoints;
    private NeroMissoes plugin;

    public PlayerPoint(NeroMissoes plugin) {
        this.plugin = plugin;
        this.hookPlayerPoints();
    }

    private boolean hookPlayerPoints() {
        final Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        return playerPoints != null;
    }

    public PlayerPoints getPlayerPoints() {
        return playerPoints;
    }

    public boolean hasPlayerPoint() {
        return playerPoints != null;
    }
}
