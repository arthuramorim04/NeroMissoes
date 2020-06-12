package com.arthuramorim.events;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMissionAccept;
import com.arthuramorim.entitys.EntityPlayer;
import com.arthuramorim.utils.player.PlayerUtil;
import com.arthuramorim.utils.utils.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MissionEvents implements Listener {

    private NeroMissoes plugin;

    public MissionEvents(NeroMissoes plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void breakBlockMission(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (e.isCancelled()) return;

        EntityPlayer entityPlayer = plugin.getHashPlayer().get(player.getName());

        if (entityPlayer.getMissionHashSet().isEmpty() || entityPlayer.getMissionHashSet() == null) return;

        for (EntityMissionAccept entityMissionAccept : entityPlayer.getMissionHashSet()) {
            if (!entityMissionAccept.missionExpired()) {

                if (entityMissionAccept.getMission().getType().equalsIgnoreCase("break")) {
                    if (entityMissionAccept.getMission().getIdItem() == e.getBlock().getType().getId() &&
                            entityMissionAccept.getMission().getDataItem() == e.getBlock().getData()) {
                        Integer oldProgress = entityMissionAccept.getProgress();
                        entityMissionAccept.setProgress(oldProgress + 1);
                        if (entityMissionAccept.getProgress() == entityMissionAccept.getMission().getQuantity()) {
                            if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("coins")) {
                                PlayerUtil.addBalance(player, entityMissionAccept.getMission().getReward());
                                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                                player.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                                        .getMissionName()));
                                return;
                            }
                            if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("ppoints")) {
                                PlayerUtil.addPpoints(player, entityMissionAccept.getMission().getReward());
                                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                                player.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                                        .getMissionName()));
                                return;
                            }

                        }
                    }
                }
            } else {
                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                player.sendMessage(TextUtil.color("&cA missao &f" + entityMissionAccept.getMission().getMissionName() + "&c expirou"));
            }
        }
    }


    @EventHandler
    public void placeBlockMission(BlockPlaceEvent e) {

        if (!(e.getPlayer() instanceof Player)) return;

        Player player = e.getPlayer();

        EntityPlayer entityPlayer = plugin.getHashPlayer().get(player.getName());

        if (entityPlayer.getMissionHashSet().isEmpty() || entityPlayer.getMissionHashSet() == null) return;

        for (EntityMissionAccept entityMissionAccept : entityPlayer.getMissionHashSet()) {
            if (!entityMissionAccept.missionExpired()) {

                if (entityMissionAccept.getMission().getType().equalsIgnoreCase("place")) {
                    if (entityMissionAccept.getMission().getIdItem() == e.getBlock().getType().getId() &&
                            entityMissionAccept.getMission().getDataItem() == e.getBlock().getData()) {

                        Integer oldProgress = entityMissionAccept.getProgress();
                        entityMissionAccept.setProgress(oldProgress + 1);
                        if (entityMissionAccept.getProgress() == entityMissionAccept.getMission().getQuantity()) {
                            if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("coins")) {
                                PlayerUtil.addBalance(player, entityMissionAccept.getMission().getReward());
                                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                                player.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                                        .getMissionName()));
                                return;
                            }
                            if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("ppoints")) {
                                PlayerUtil.addPpoints(player, entityMissionAccept.getMission().getReward());
                                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                                player.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                                        .getMissionName()));
                                return;
                            }
                        }
                    }
                }
            } else {
                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                player.sendMessage(TextUtil.color("&cA missao &f" + entityMissionAccept.getMission().getMissionName() + "&c expirou"));
            }
        }

    }

    @EventHandler
    public void playerDeathMission(EntityDeathEvent e) {

        Player killer = e.getEntity().getKiller();

        if (!(e.getEntity().getKiller() instanceof Player)) return;

        EntityPlayer entityPlayer = plugin.getHashPlayer().get(killer.getName());


        for (EntityMissionAccept entityMissionAccept : entityPlayer.getMissionHashSet()) {
            if (entityMissionAccept.getMission().getType().equalsIgnoreCase("pvp")) {
                Integer oldProgress = entityMissionAccept.getProgress();

                entityMissionAccept.setProgress(oldProgress + 1);

                if (entityMissionAccept.getProgress() == entityMissionAccept.getMission().getQuantity()) {
                    if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("coins")) {
                        PlayerUtil.addBalance(killer, entityMissionAccept.getMission().getReward());
                        entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                        killer.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                                .getMissionName()));
                        return;
                    }
                    if (entityMissionAccept.getMission().getTypeReward().equalsIgnoreCase("ppoints")) {
                        PlayerUtil.addPpoints(killer, entityMissionAccept.getMission().getReward());
                        entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                        killer.sendMessage(TextUtil.color("&aVocê completou a missão " + entityMissionAccept.getMission()
                                .getMissionName()));
                        return;
                    }
                }
            }
        }
    }


}

