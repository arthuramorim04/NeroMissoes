package com.arthuramorim.menus;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMission;
import com.arthuramorim.entitys.EntityMissionAccept;
import com.arthuramorim.entitys.EntityPlayer;
import com.github.eokasta.core.utils.inventorygui.buttons.ClickAction;
import com.github.eokasta.core.utils.inventorygui.buttons.ItemButton;
import com.github.eokasta.core.utils.inventorygui.menus.InventoryGUI;
import com.github.eokasta.core.utils.utils.MakeItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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

        for (EntityMission entityMission : plugin.getHashMission()) {
            MakeItem missionItem = new MakeItem(entityMission.getIdItem(), entityMission.getDataItem());
            missionItem.addLore((ArrayList<String>) entityMission.getDescription());
            missionItem.setName(entityMission.getMissionName());
            ItemButton mission = new ItemButton(missionItem.build());
            mission.addAction(ClickType.LEFT, ClickAction ->{
                //erro ao registrar os eventos do inv, ver com o kasta
                addMission(p,entityMission);
                p.sendMessage("a");
            });
            setButton(entityMission.getSlot(), mission);
        }

        menu.setButton(40, arrowButton);
        menu.setButton(0, lanterButton);
        menu.setButton(8, lanterButton);
        menu.setButton(36, lanterButton);
        menu.setButton(44, lanterButton);
        menu.setButton(1, webButton);
        menu.setButton(7, webButton);
        menu.setButton(9, webButton);
        menu.setButton(17, webButton);
        menu.setButton(27, webButton);
        menu.setButton(35, webButton);
        menu.setButton(37, webButton);
        menu.setButton(43, webButton);


    }

    public void openMenuMission(Player p) {
        createInvMission(p);
        menu.show(p);
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
                if (missionAccept.getMissionName().equals(itemMission.getItemMeta().getDisplayName())) {
                    EntityMissionAccept missionTemp = new EntityMissionAccept();
                    missionTemp.setMission(missionAccept);
                    missionTemp.setProgress(0);

                    EntityPlayer entityPlayer = plugin.getHashPlayer().get(p.getName());
                    entityPlayer.getMissionHashSet().add(missionTemp);
                    p.sendMessage("&bMissao " + missionTemp.getMission().getMissionName() + " adicionada com sucesso");
                    p.closeInventory();
                }
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

    public void progressMission(Player p, ItemStack itemMission) {
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
        createInvAcceptMission(p, itemMission);
        acceptMission.show(p);

    }


    public void addMission(Player p,EntityMission mission){
        if(hasMission(p,mission)){
            p.sendMessage("a");
        }else{
            p.sendMessage("b");
        }
    }

    public boolean hasMission(Player p,EntityMission mission){
        for (EntityMissionAccept entityMissionAccept : plugin.getHashPlayer().get(p.getName()).getMissionHashSet()) {
            if(entityMissionAccept.getMission() == mission){
                return true;
            }else{
                continue;
            }
        }
        return false;
    }

}
