package com.arthuramorim.menus;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMission;
import com.arthuramorim.entitys.EntityMissionAccept;
import com.arthuramorim.entitys.EntityPlayer;
import com.github.eokasta.core.utils.inventorygui.PaginatedGUIBuilder;
import com.github.eokasta.core.utils.inventorygui.buttons.ItemButton;
import com.github.eokasta.core.utils.inventorygui.menus.InventoryGUI;
import com.github.eokasta.core.utils.utils.MakeItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MissionMenus extends InventoryGUI implements Listener {

    private ItemStack seaLantern = new MakeItem(Material.SEA_LANTERN).setName("").build();
    private ItemStack web = new MakeItem(Material.WEB).setName("").build();
    private ItemStack background = new MakeItem(Material.STAINED_GLASS_PANE, (byte) 15).build();
    private ItemStack arrow = new MakeItem(Material.ARROW).setName("&fSair").build();
    private InventoryGUI menu;
    private InventoryGUI acceptMission;
    private InventoryGUI menuMission;
    private NeroMissoes plugin;


    public MissionMenus(NeroMissoes plugin) {
        super("Missoes", 9 * 5);
        menu = this;
        this.plugin = plugin;
        plugin.registerListener(this);
    }

    private void createInvMission(Player p) {

        List<ItemButton> list = new ArrayList<>();

        for (EntityMission entityMission : plugin.getHashMission()) {
            MakeItem missionItem = new MakeItem(entityMission.getIdItem(), entityMission.getDataItem());
            missionItem.addLore((ArrayList<String>) entityMission.getDescription());
            missionItem.setName(entityMission.getMissionName());
            ItemButton mission = new ItemButton(missionItem.build());
            mission.addAction(ClickType.LEFT, ClickAction -> {

                EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());

                if(entityPlayer.getMissionHashSet() == null || entityPlayer.getMissionHashSet().isEmpty()){

                    p.sendMessage("nao possui a missao");

                    if(addMission(entityPlayer,entityMission)){

                        p.sendMessage("Missao : " + entityMission.getMissionName() + " Adicionada com sucesso!");

                    }else{
                        p.sendMessage("Falha ao adicionar a missao : " + entityMission.getMissionName());
                    }
                }else{
                    if(entityPlayer.hasMission(entityMission)){
                        p.sendMessage("Voce ja possui essa missao");
                    }else{
                        if(addMission(entityPlayer,entityMission)){
                            p.sendMessage("Missao : " + entityMission.getMissionName() + " Adicionada com sucesso!");
                        }else{
                            p.sendMessage("Falha ao adicionar a missao : " + entityMission.getMissionName());
                        }
                    }

                }
            });
           list.add(mission);
        }

        PaginatedGUIBuilder paginatedGUIBuilder = new PaginatedGUIBuilder("&bMissoes",
                "xxxxxxxxx" +
                        "x#######x" +
                        "x#######x" +
                        "<#######>" +
                        "X#######X"

        );
        paginatedGUIBuilder.setContent(list);
        paginatedGUIBuilder.setNextPageItem(Material.ARROW, 1, "Proxima");
        paginatedGUIBuilder.setPreviousPageItem(Material.ARROW, 1, "Voltar");

        paginatedGUIBuilder.build().show(p);


    }

    public void openMissionList(Player p) {
        createInvMission(p);
    }

    public void menuMission(Player p) {
        ItemButton playerMission = new ItemButton(new MakeItem(p.getName()).setName(p.getName()).build()).setDefaultAction(ClickAction ->{
            playerMissions(p);
        });
        ItemButton listMission = new ItemButton(new MakeItem(339).addGlow().build()).setDefaultAction(ClickAction ->{
            openMissionList(p);
        });
        ItemButton arrowButton = new ItemButton(arrow);
        arrowButton.setDefaultAction(ClickAction -> {
            p.closeInventory();
        });
        menuMission = new InventoryGUI("&aMissoes",3*9);


        menuMission.setButton(22, arrowButton);
        menuMission.setButton(11, playerMission);
        menuMission.setButton(15, listMission);
        menuMission.show(p);
    }

    public void openMenuMission(Player p) {
        menuMission(p);
    }


    public boolean addMission(EntityPlayer p, EntityMission mission) {

        try{
            EntityMissionAccept accept = new EntityMissionAccept();
            accept.setMission(mission);
            accept.setProgress(0);
            p.getMissionHashSet().add(accept);
            return true;
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }


    //testes

    public void playerMissions(Player p) {
        List<ItemButton> playerMissions = new ArrayList<>();

        EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());
        MakeItem icon = null;

        for(EntityMissionAccept mission : entityPlayer.getMissionHashSet()){
            icon = new MakeItem(mission.getMission().getIdItem(),mission.getMission().getDataItem());
            String progress = String.valueOf(mission.getProgress());
            String missionName = mission.getMission().getMissionName();
            String quantity = String.valueOf(mission.getMission().getQuantity());
            String reward = String.valueOf(mission.getMission().getReward());

            List<String> list = new ArrayList<>();
            list.add(missionName);
            list.add(" ");
            list.add(quantity);
            list.add(progress);
            list.add(reward);

            icon.setLore(list);

            ItemButton itemButton = new ItemButton(icon.build());

            playerMissions.add(itemButton);

        }

        PaginatedGUIBuilder paginatedGUIBuilder = new PaginatedGUIBuilder("&aSuas Missoes",
                "xxxxxxxxx" +
                        "x#######x" +
                        "<#######>" +
                        "X#######X"

        );
        paginatedGUIBuilder.setContent(playerMissions);
        paginatedGUIBuilder.setNextPageItem(Material.ARROW, 1, "Proxima pagina");
        paginatedGUIBuilder.setPreviousPageItem(Material.ARROW, 1, "Pagina Anterior");

        paginatedGUIBuilder.build().show(p);
    }

}
