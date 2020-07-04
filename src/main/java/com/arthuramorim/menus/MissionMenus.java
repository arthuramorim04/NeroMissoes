package com.arthuramorim.menus;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMission;
import com.arthuramorim.entitys.EntityMissionAccept;
import com.arthuramorim.entitys.EntityPlayer;
import com.arthuramorim.utils.inventoryGUI.PaginatedGUIBuilder;
import com.arthuramorim.utils.inventoryGUI.buttons.ItemButton;
import com.arthuramorim.utils.inventoryGUI.menus.InventoryGUI;
import com.arthuramorim.utils.utils.MakeItem;
import com.arthuramorim.utils.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
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

                if (entityPlayer.getMissionHashSet() == null || entityPlayer.getMissionHashSet().isEmpty()) {

                    if (addMission(entityPlayer, entityMission)) {

                        p.sendMessage("Missao : " + entityMission.getMissionName() + " Adicionada com sucesso!");

                    } else {
                        p.sendMessage("Falha ao adicionar a missao : " + entityMission.getMissionName());
                    }
                } else {
                    if (entityPlayer.hasMission(entityMission)) {

                    } else {
                        if (addMission(entityPlayer, entityMission)) {
                            p.sendMessage(TextUtil.color("&aMissao : " + entityMission.getMissionName() + " &aAdicionada com sucesso!"));
                        } else {
                            p.sendMessage(TextUtil.color("&cFalha ao adicionar a missao : &e" + entityMission.getMissionName()));
                        }
                    }

                }
            });
            list.add(mission);
        }

        PaginatedGUIBuilder paginatedGUIBuilder = new PaginatedGUIBuilder("&bLista de Missoes",
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
        ItemButton playerMission = new ItemButton(new MakeItem(p.getName()).setName("&eSuas Missoes").build()).setDefaultAction(ClickAction -> {
            playerMissions(p);
        });
        ItemButton listMission = new ItemButton(new MakeItem(339).addGlow().setName("&eLista de Missoes").build()).setDefaultAction(ClickAction -> {
            openMissionList(p);
        });
        ItemButton arrowButton = new ItemButton(arrow);
        arrowButton.setDefaultAction(ClickAction -> {
            p.closeInventory();
        });
        menuMission = new InventoryGUI("&aMissoes", 3 * 9);


        menuMission.setButton(22, arrowButton);
        menuMission.setButton(11, playerMission);
        menuMission.setButton(15, listMission);
        menuMission.show(p);
    }

    public void openMenuMission(Player p) {
        menuMission(p);
    }


    public boolean addMission(EntityPlayer p, EntityMission mission) {

        try {
            EntityMissionAccept accept = new EntityMissionAccept();
            accept.setMission(mission);
            accept.setProgress(0);
            accept.addTimeExpired();
            p.getMissionHashSet().add(accept);
            return true;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return false;
        }

    }


    //testes

    public void playerMissions(Player p) {
        List<ItemButton> playerMissions = new ArrayList<>();
        List<EntityMissionAccept> removeMission = new ArrayList<>();

        EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());
        MakeItem icon = null;

        for (EntityMissionAccept mission : entityPlayer.getMissionHashSet()) {


            icon = new MakeItem(mission.getMission().getIdItem(), mission.getMission().getDataItem());
            String progress = String.valueOf(mission.getProgress());
            String missionName = mission.getMission().getMissionName();
            String quantity = String.valueOf(mission.getMission().getQuantity());
            String reward = String.valueOf(mission.getMission().getReward());

            ArrayList<String> list = new ArrayList<>();
            list.add(" ");
            list.add("&eProgresso: &f" + progress + "/" + quantity);
            list.add("&ePremio: &f" + reward + " " + mission.getMission().getTypeReward());
            list.add(" ");
            if (mission.getMission().getExpired() > 0 || mission.getMission().getExpired() == 0) {
                if(mission.getMission().getExpired() == 0){
                    list.add("&eExpira em:&f nunca");
                }else{
                    list.add("&eExpira em: &f" + mission.getRemainingTime() + " minutos");
                }


                icon.setName(missionName);
                icon.setLore(list);

                ItemButton itemButton = new ItemButton(icon.build());

                if (mission.missionExpired()) {
                    removeMission.add(mission);
                } else {
                    playerMissions.add(itemButton);

                }
            }
        }

        PaginatedGUIBuilder paginatedGUIBuilder = new PaginatedGUIBuilder("&0Suas Missoes",
                "xxxxxxxxx" +
                        "x#######x" +
                        "<#######>" +
                        "X#######X"

        );
        paginatedGUIBuilder.setContent(playerMissions);
        paginatedGUIBuilder.setNextPageItem(Material.ARROW, 1, "Proxima pagina");
        paginatedGUIBuilder.setPreviousPageItem(Material.ARROW, 1, "Pagina Anterior");

        if (!removeMission.isEmpty()) {
            p.sendMessage(TextUtil.color("&cAlgumas missoes expiraram: \n"));
            for (EntityMissionAccept entityMissionAccept : removeMission) {
                entityPlayer.getMissionHashSet().remove(entityMissionAccept);
                p.sendMessage(TextUtil.color("&c"+entityMissionAccept.getMission().getMissionName()));
            }
        }

        paginatedGUIBuilder.build().show(p);
    }

}
