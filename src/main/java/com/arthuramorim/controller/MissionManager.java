package com.arthuramorim.controller;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class MissionManager {

    private NeroMissoes plugin;

    public MissionManager(NeroMissoes plugin) {
        this.plugin = plugin;
        loadMission();
    }

    public void loadMission() {
        try {

            plugin.getMissoes().getConfigFile().getConfigurationSection("mission").getKeys(false).forEach(mission -> {

                FileConfiguration path = plugin.getMissoes().getConfigFile();
                Integer requirement = path.getInt("mission." + mission + ".requirement");
                String missionName = path.getString("mission." + mission + ".missionName");
                Integer reward = path.getInt("mission." + mission + ".reward");
                Integer slot = path.getInt("mission." + mission + ".slot");
                Integer expired = path.getInt("mission." + mission + ".expired");
                String type = path.getString("mission." + mission + ".type");
                String typeReward = path.getString("mission." + mission + ".typeReward");
                Integer idItem = path.getInt("mission." + mission + ".idItem");
                byte dataItem = (byte) path.getInt("mission." + mission + ".dataItem");
                Integer quantity = path.getInt("mission." + mission + ".quantity");
                List<String> description = path.getStringList("mission." + mission + ".description");
                EntityMission entityMission = new EntityMission(missionName, type, quantity, reward, typeReward, expired, idItem, dataItem, quantity, slot, description);
                plugin.getHashMission().add(entityMission);
            });

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
