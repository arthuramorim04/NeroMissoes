package com.arthuramorim.controller;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMissionAccept;
import com.arthuramorim.entitys.EntityPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;

public class MissionPlayerController {


    private Gson gson = new Gson();


    private NeroMissoes plugin;

    public MissionPlayerController(NeroMissoes plugin) {
        this.plugin = (NeroMissoes) plugin;
    }

    private Connection con;

    public void registerNewPlayer(Player p) {

        EntityPlayer player = new EntityPlayer(p.getUniqueId(), p.getName());
        HashSet<EntityMissionAccept> missionList = new HashSet<>();
        player.setMissionHashSet(missionList);
        JsonElement jsonPlayer = gson.toJsonTree(player);
        con = plugin.getDbConnection().getConnection();

        try {
            PreparedStatement ps;

            if (player.getUuid() != null && player.getName() != null) {
                ps = con.prepareStatement("insert into neromissoes" +
                        "(name,uuid,missoes)" +
                        "values('" + p.getName() + "','" + p.getUniqueId() + "','" + jsonPlayer + "')");

                ps.execute();
                ps.close();
                plugin.getHashPlayer().put(p.getName(), player);
            } else {
                System.out.println("uuid ou nome de jogador nulo.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadPlayer(Player p) {
        boolean existPlayer = false;
        EntityPlayer player = new EntityPlayer(p.getUniqueId(), p.getName());
        con = plugin.getDbConnection().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("select name, uuid, missoes from neromissoes where name = '" + p.getName() + "'");
            ResultSet result = ps.executeQuery();

            while (result.next()) {

                EntityPlayer loadPlayer = new EntityPlayer();
                String missoes = result.getString("missoes");
                loadPlayer = gson.fromJson(result.getString("missoes"), EntityPlayer.class);

                plugin.getHashPlayer().put(result.getString("name"), loadPlayer);
                existPlayer = true;

            }
            if (existPlayer == false) {
                registerNewPlayer(p);
            }

        } catch (NullPointerException e) {
            registerNewPlayer(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void savePlayer(EntityPlayer p) {

        con = plugin.getDbConnection().getConnection();

        try {
            JsonElement s = gson.toJsonTree(p);
            PreparedStatement ps = con.prepareStatement("update neromissoes " +
                    "set missoes = '" + s + "' where name = '" + p.getName() + "';");
            ps.execute();
            plugin.getHashPlayer().remove(p.getName());

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
}
