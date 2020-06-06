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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MissionMenus extends InventoryGUI {

    private ItemStack seaLantern = new MakeItem(Material.SEA_LANTERN).setName("").build();
    private ItemStack web = new MakeItem(Material.WEB).setName("").build();
    private ItemStack background = new MakeItem(Material.STAINED_GLASS_PANE, (byte) 15).build();
    private ItemStack arrow = new MakeItem(Material.ARROW).setName("&fSair").build();
    private InventoryGUI menu;
    private InventoryGUI acceptMission;
    private InventoryGUI progressMission;
    private NeroMissoes plugin;


    public MissionMenus(NeroMissoes plugin) {
        super("Missoes", 9 * 5);
        menu = this;
        this.plugin = plugin;
    }

    private void createInvMission(Player p) {

        List<ItemButton> list = new ArrayList<>();

        for (EntityMission entityMission : plugin.getHashMission()) {
            MakeItem missionItem = new MakeItem(entityMission.getIdItem(), entityMission.getDataItem());
            missionItem.addLore((ArrayList<String>) entityMission.getDescription());
            missionItem.setName(entityMission.getMissionName());
            ItemButton mission = new ItemButton(missionItem.build());
            mission.addAction(ClickType.LEFT, ClickAction -> {
                    openMissionProgress(p,missionItem.build());
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
        paginatedGUIBuilder.setNextPageItem(Material.ARROW, 1, "Proxima pagina");
        paginatedGUIBuilder.setPreviousPageItem(Material.ARROW, 1, "Pagina Anterior");

        paginatedGUIBuilder.build().show(p);


    }

    public void openMenuMission(Player p) {
        createInvMission(p);
    }

    public void createInvAcceptMission(Player p, ItemStack itemMission) {
        ItemButton lanterButton = new ItemButton(seaLantern);
        ItemButton webButton = new ItemButton(web);
        ItemButton arrowButton = new ItemButton(arrow);
        ItemButton backgroundButton = new ItemButton(background);
        arrowButton.setDefaultAction(ClickAction -> {
            p.closeInventory();
        });

        for (int i = 0; i < menu.getInventory().getSize(); i++) {
            menu.setButton(i, backgroundButton);
        }


        ItemButton mission = new ItemButton(itemMission);
        acceptMission.setButton(13, mission);

        MakeItem accept = new MakeItem(35, (byte) 5).setName("&aAceitar missao");
        MakeItem cancel = new MakeItem(35, (byte) 5).setName("&aCancelar");

        ItemButton acceptButton = new ItemButton(accept.build());
        ItemButton cancelButton = new ItemButton(cancel.build());

        acceptButton.setDefaultAction(ClickAction -> {
            for (EntityMission missionAccept : plugin.getHashMission()) {
                    addMission(p,missionAccept);
            }
        });



        cancelButton.setDefaultAction(ClickAction -> {
            p.closeInventory();
        });

        acceptMission.setButton(11, acceptButton);
        acceptMission.setButton(15, cancelButton);


        acceptMission.setButton(40, arrowButton);
        acceptMission.setButton(0, lanterButton);
        acceptMission.setButton(8, lanterButton);
        acceptMission.setButton(18, lanterButton);
        acceptMission.setButton(26, lanterButton);
        acceptMission.setButton(1, webButton);
        acceptMission.setButton(7, webButton);
        acceptMission.setButton(9, webButton);
        acceptMission.setButton(17, webButton);
        acceptMission.setButton(25, webButton);
        acceptMission.setButton(19, webButton);
    }

    public void openAcceptMission(Player p, ItemStack itemMission) {
        createInvAcceptMission(p, itemMission);
        acceptMission.show(p);

    }

    public void createProgressMission(Player p, ItemStack itemMission) {
        ItemButton lanterButton = new ItemButton(seaLantern);
        ItemButton webButton = new ItemButton(web);
        ItemButton arrowButton = new ItemButton(arrow);
        ItemButton backgroundButton = new ItemButton(background);
        arrowButton.setDefaultAction(ClickAction -> {
            p.closeInventory();
        });

        for (int i = 0; i < menu.getInventory().getSize(); i++) {
            progressMission.setButton(i, backgroundButton);
        }


        plugin.getHashPlayer().get(p.getName()).getMissionHashSet().forEach(missionTemp -> {
            if (missionTemp.getMission().getMissionName().equals(itemMission.getItemMeta().getDisplayName())) {

                ItemMeta itemMeta = itemMission.getItemMeta();
                List<String> lore = itemMeta.getLore();
                lore.add(" ");
                lore.add("&eProgresso: " + missionTemp.getProgress() + "/" + missionTemp.getMission().getQuantity());
                itemMeta.setLore(lore);

                itemMission.setItemMeta(itemMeta);

                ItemButton mission = new ItemButton(itemMission);
                progressMission.setButton(13, mission);
            }
        });


        progressMission.setButton(40, arrowButton);
        progressMission.setButton(0, lanterButton);
        progressMission.setButton(8, lanterButton);
        progressMission.setButton(18, lanterButton);
        progressMission.setButton(26, lanterButton);
        progressMission.setButton(1, webButton);
        progressMission.setButton(7, webButton);
        progressMission.setButton(9, webButton);
        progressMission.setButton(17, webButton);
        progressMission.setButton(25, webButton);
        progressMission.setButton(19, webButton);
    }

    public void openMissionProgress(Player p, ItemStack itemMission) {
        createProgressMission(p, itemMission);
        progressMission.show(p);

    }


    public void addMission(Player p, EntityMission mission) {
        EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());
        EntityMissionAccept entityMissionAccept = new EntityMissionAccept();
        entityMissionAccept.setMission(mission);
        entityMissionAccept.setProgress(0);
        entityPlayer.getMissionHashSet().add(entityMissionAccept);
        p.closeInventory();
        p.sendMessage("&aVoce recebeu uma nova missao");
    }

    public boolean hasMission(Player p, EntityMission mission) {
        boolean hasMission = false;
        EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());
        HashSet<EntityMissionAccept> missionHashSet = entityPlayer.getMissionHashSet();
        return hasMission;
    }


    //testes

    public void teste(Player p) {
        List<ItemButton> list = new ArrayList<>();
        list.add(new ItemButton(Material.CARROT, 1, "1"));
        list.add(new ItemButton(Material.SEA_LANTERN, 1, "2"));
        list.add(new ItemButton(Material.DIAMOND, 1, "3"));
        list.add(new ItemButton(Material.DIAMOND, 1, "4"));
        list.add(new ItemButton(Material.DIAMOND, 1, "5"));
        list.add(new ItemButton(Material.DIAMOND, 1, "6"));
        list.add(new ItemButton(Material.DIAMOND, 1, "7"));
        list.add(new ItemButton(Material.DIAMOND, 1, "8"));
        list.add(new ItemButton(Material.DIAMOND, 1, "9"));
        list.add(new ItemButton(Material.DIAMOND, 1, "11"));
        list.add(new ItemButton(Material.DIAMOND, 1, "12"));
        list.add(new ItemButton(Material.DIAMOND, 1, "13"));
        list.add(new ItemButton(Material.DIAMOND, 1, "14"));
        list.add(new ItemButton(Material.DIAMOND, 1, "15"));
        list.add(new ItemButton(Material.DIAMOND, 1, "16"));
        list.add(new ItemButton(Material.DIAMOND, 1, "17"));
        list.add(new ItemButton(Material.DIAMOND, 1, "18"));
        list.add(new ItemButton(Material.DIAMOND, 1, "19"));
        list.add(new ItemButton(Material.DIAMOND, 1, "20"));
        list.add(new ItemButton(Material.DIAMOND, 1, "21"));
        list.add(new ItemButton(Material.DIAMOND, 1, "22"));
        list.add(new ItemButton(Material.DIAMOND, 1, "23"));
        list.add(new ItemButton(Material.DIAMOND, 1, "24"));
        list.add(new ItemButton(Material.DIAMOND, 1, "25"));
        list.add(new ItemButton(Material.DIAMOND, 1, "26"));
        list.add(new ItemButton(Material.DIAMOND, 1, "27"));
        list.add(new ItemButton(Material.DIAMOND, 1, "28"));
        list.add(new ItemButton(Material.DIAMOND, 1, "29"));
        list.add(new ItemButton(Material.DIAMOND, 1, "30"));
        list.add(new ItemButton(Material.DIAMOND, 1, "31"));
        list.add(new ItemButton(Material.DIAMOND, 1, "32"));
        PaginatedGUIBuilder paginatedGUIBuilder = new PaginatedGUIBuilder("Teste",
                "xxxxxxxxx" +
                        "x#######x" +
                        "x#######x" +
                        "<#######>" +
                        "X#######X"

        );
        paginatedGUIBuilder.setContent(list);
        paginatedGUIBuilder.setNextPageItem(Material.ARROW, 1, "Proxima pagina");
        paginatedGUIBuilder.setPreviousPageItem(Material.ARROW, 1, "Pagina Anterior");

        paginatedGUIBuilder.build().show(p);
    }

}
