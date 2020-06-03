package com.arthuramorim;

import com.arthuramorim.commands.Commands;
import com.arthuramorim.configs.ConfigsAPI;
import com.arthuramorim.controller.MissionManager;
import com.arthuramorim.controller.MissionPlayerController;
import com.arthuramorim.database.DBManager;
import com.arthuramorim.entitys.EntityMission;
import com.arthuramorim.entitys.EntityPlayer;
import com.arthuramorim.events.EventsPlayer;
import com.arthuramorim.menus.MissionMenus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;

public class NeroMissoes extends JavaPlugin {

    private ConfigsAPI configDefault;
    private NeroMissoes plugin;
    private ConfigsAPI missoes;
    private MissionMenus missionMenus;
    private DBManager dbConnection;
    private MissionPlayerController mpc;
    private HashMap<String, EntityPlayer> hashPlayer = new HashMap<>();
    private HashSet<EntityMission> hashMission = new HashSet<>();
    private MissionManager missionManager;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "\n" +
                "             __   __     ______     ______     ______                      \n" +
                "            /\\ \"-.\\ \\   /\\  ___\\   /\\  == \\   /\\  __ \\                     \n" +
                "            \\ \\ \\-.  \\  \\ \\  __\\   \\ \\  __<   \\ \\ \\/\\ \\                    \n" +
                "             \\ \\_\\\\\"\\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_____\\                   \n" +
                "              \\/_/ \\/_/   \\/_____/   \\/_/ /_/   \\/_____/                   \n" +
                "                                                                           \n" +
                " __    __     __     ______     ______     ______     ______     ______    \n" +
                "/\\ \"-./  \\   /\\ \\   /\\  ___\\   /\\  ___\\   /\\  __ \\   /\\  ___\\   /\\  ___\\   \n" +
                "\\ \\ \\-./\\ \\  \\ \\ \\  \\ \\___  \\  \\ \\___  \\  \\ \\ \\/\\ \\  \\ \\  __\\   \\ \\___  \\  \n" +
                " \\ \\_\\ \\ \\_\\  \\ \\_\\  \\/\\_____\\  \\/\\_____\\  \\ \\_____\\  \\ \\_____\\  \\/\\_____\\ \n" +
                "  \\/_/  \\/_/   \\/_/   \\/_____/   \\/_____/   \\/_____/   \\/_____/   \\/_____/ \n\n" +
                "");
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[NeroMissoes]" + "INICIALIZANDO...");

        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[NeroMissoes]" + "Carregando configuracoes");
        configDefault = new ConfigsAPI(this,"config");
        missoes = new ConfigsAPI(this,"missoes");

        String user = configDefault.getConfigFile().getString("storage.user");
        String pass = configDefault.getConfigFile().getString("storage.pass");
        String host = configDefault.getConfigFile().getString("storage.host");
        String db = configDefault.getConfigFile().getString("storage.db");
        Integer port = configDefault.getConfigFile().getInt("storage.port");
        dbConnection = new DBManager(user, pass, host, port, db);
        dbConnection.openConnection();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[NeroMissoes]" + "Carregando configuracoes finalizado com sucesso.");


        missionManager = new MissionManager(this);



        Bukkit.getPluginManager().registerEvents(new EventsPlayer(this),this);
        getCommand("missoes").setExecutor(new Commands(this));
        getCommand("missoesreload").setExecutor(new Commands(this));
        getCommand("teste").setExecutor(new Commands(this));
        new Commands(this);

        missionMenus = new MissionMenus(this);

        mpc = new MissionPlayerController(this);


    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public ConfigsAPI getConfigDefault() {
        return configDefault;
    }

    public ConfigsAPI getMissoes() {
        return missoes;
    }

    public MissionMenus getMissionMenus() {
        return missionMenus;
    }

    public DBManager getDbConnection() {
        return dbConnection;
    }

    public MissionPlayerController getMpc() {
        return mpc;
    }

    public HashMap<String, EntityPlayer> getHashPlayer() {
        return hashPlayer;
    }

    public HashSet<EntityMission> getHashMission() {
        return hashMission;
    }

    public MissionManager getMissionManager() {
        return missionManager;
    }

    public void setMissionMenus(MissionMenus missionMenus) {
        this.missionMenus = missionMenus;
    }
}