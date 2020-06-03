package com.arthuramorim.controller;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityPlayer;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MissionPlayerController {


    private Gson gson = new Gson();


    private NeroMissoes plugin;

    public MissionPlayerController(NeroMissoes plugin){
        this.plugin = (NeroMissoes) plugin;
    }
    private Connection con;

    public void registerNewPlayer(Player p){

        EntityPlayer player = new EntityPlayer(p.getUniqueId(), p.getName());
        String jsonPlayer = gson.toJson(player);
        con = plugin.getDbConnection().getConnection();

        try{
            PreparedStatement ps;

            if(player.getUuid() != null && player.getName() != null){
                ps = con.prepareStatement("insert into neromissoes" +
                                              "(name,uuid,missoes)" +
                                              "values('"+p.getName()+"','"+p.getUniqueId()+"','"+jsonPlayer+"')");

                ps.execute();
                ps.close();
                plugin.getHashPlayer().put(p.getName(),player);
            }else{
                System.out.println("uuid ou nome de jogador nulo.");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void loadPlayer(Player p){
        boolean existPlayer = false;
        EntityPlayer player = new EntityPlayer(p.getUniqueId(), p.getName());
        con = plugin.getDbConnection().getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("select name, uuid, missoes from neromissoes where name = '"+p.getName()+"'");
            ResultSet result = ps.executeQuery();

            while (result.next()){
                //salvar as informacoes da querry no objeto novamente

                EntityPlayer loadPlayer = new EntityPlayer();
                String missoes = result.getString("missoes");
                plugin.getServer().getConsoleSender().sendMessage(missoes.toString());
                loadPlayer = gson.fromJson(result.getString("missoes"),EntityPlayer.class);

                plugin.getHashPlayer().put(result.getString("name"),loadPlayer);
                existPlayer = true;

            }
            if(existPlayer == false){
                registerNewPlayer(p);
            }

        }catch (NullPointerException e){
            registerNewPlayer(p);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
