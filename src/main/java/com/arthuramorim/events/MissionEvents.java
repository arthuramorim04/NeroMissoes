package com.arthuramorim.events;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMission;
import com.arthuramorim.entitys.EntityMissionAccept;
import com.arthuramorim.entitys.EntityPlayer;
import com.arthuramorim.utils.player.PlayerUtil;
import com.arthuramorim.utils.utils.TextUtil;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashSet;
import java.util.Objects;

public class MissionEvents implements Listener {

    private final NeroMissoes plugin;

    public MissionEvents(NeroMissoes plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void breakBlockMission(BlockBreakEvent e) {
        checkMissionAndProcess(e.getPlayer(), e.isCancelled(), e.getBlock(), "break");
    }


    @EventHandler
    public void placeBlockMission(BlockPlaceEvent e) {
        checkMissionAndProcess(e.getPlayer(), e.isCancelled(), e.getBlock(), "place");

    }

    @EventHandler
    public void playerDeathMission(EntityDeathEvent e) {

        Player killer = e.getEntity().getKiller();

        if (e.getEntity().getKiller() == null) return;

        EntityPlayer entityPlayer = plugin.getHashPlayer().get(killer.getName());
        HashSet<EntityMissionAccept> missions = entityPlayer.getMissionHashSet();
        if (missions.isEmpty()) return;

        missionProcess(killer, null, missions, "place");

    }


    private boolean equalsId(Integer itemId1, Integer itemId2) {
        return Objects.equals(itemId1, itemId2);
    }

    private boolean equalData(byte itemData1, byte itemData2) {
        return itemData1 == itemData2;
    }

    private void checkMissionAndProcess(Player player, boolean cancelled, Block block, String type) {
        if (cancelled) return;
        EntityPlayer entityPlayer = plugin.getHashPlayer().get(player.getName());
        HashSet<EntityMissionAccept> missions = entityPlayer.getMissionHashSet();
        if (missions.isEmpty()) return;
        missionProcess(player, block, missions, type);
    }

    private void missionProcess(Player player, Block block, HashSet<EntityMissionAccept> missions, String type) {
        for (EntityMissionAccept entityMissionAccept : missions) {
            if (!entityMissionAccept.missionExpired()) {
                if ((block != null && isCorrectBlockAndMissionType(entityMissionAccept, block, type)) || type.equalsIgnoreCase("pvp")) {
                    updateMissionProgress(entityMissionAccept);
                    if (checkIfMissionFinished(entityMissionAccept)) {
                        removeMission(entityMissionAccept, missions);
                        sendRewards(entityMissionAccept, player);
                    }
                    return;
                }
            } else {
                missions.remove(entityMissionAccept);
                player.sendMessage(TextUtil.color("&cA missão &f" + entityMissionAccept.getMission().getMissionName() + "&c expirado"));
            }
        }
    }

    private void updateMissionProgress(EntityMissionAccept entityMissionAccept) {
        entityMissionAccept.setProgress(entityMissionAccept.getProgress() + 1);
    }

    private boolean isCorrectBlockAndMissionType(EntityMissionAccept entityMissionAccept, Block block, String type) {
        EntityMission mission = entityMissionAccept.getMission();
        return entityMissionAccept.getMission().getType().equalsIgnoreCase(type)
                && equalsId(mission.getIdItem(), block.getType().getId()) && equalData(mission.getDataItem(), block.getData());
    }

    private boolean checkIfMissionFinished(EntityMissionAccept entityMissionAccept) {
        return Objects.equals(entityMissionAccept.getProgress(), entityMissionAccept.getMission().getQuantity());
    }

    private void removeMission(EntityMissionAccept entityMissionAccept, HashSet<EntityMissionAccept> missions) {
        missions.remove(entityMissionAccept);
    }

    private void sendRewards(EntityMissionAccept entityMissionAccept, Player player) {
        if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("coins")) {
            PlayerUtil.addBalance(player, entityMissionAccept.getMission().getReward());
            player.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                    .getMissionName()));
        }
        if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("ppoints")) {
            PlayerPoints playerPoints = plugin.getPlayerPoints().getPlayerPoints();
            if (playerPoints != null) {
                playerPoints.getAPI().give(player.getUniqueId(), entityMissionAccept.getMission().getReward());
                player.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                        .getMissionName()));
            } else {
                player.sendMessage(TextUtil.color("&aNão foi possível enviar sua recompensa, " +
                        "entre em contato com um administrador ou superior"));
            }
        }
    }
}

