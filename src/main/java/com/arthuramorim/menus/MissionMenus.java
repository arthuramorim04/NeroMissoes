package com.arthuramorim.menus;

import com.arthuramorim.NeroMissoes;
import com.arthuramorim.entitys.EntityMission;
import com.github.eokasta.core.utils.inventorygui.buttons.ItemButton;
import com.github.eokasta.core.utils.inventorygui.menus.InventoryGUI;
import com.github.eokasta.core.utils.utils.MakeItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MissionMenus extends InventoryGUI {

    private ItemStack seaLantern = new MakeItem(Material.SEA_LANTERN).setName("").build();
    private ItemStack web = new MakeItem(Material.WEB).setName("").build();
    private ItemStack background = new MakeItem(Material.STAINED_GLASS_PANE,(byte) 15).build();
    private ItemStack arrow = new MakeItem(Material.ARROW).setName("&fSair").build();
    private InventoryGUI menu;
    private NeroMissoes plugin;

    public MissionMenus(NeroMissoes plugin) {
        super("Missoes", 9*5);
        menu = this;
        this.plugin = plugin;
    }

    private void createInvMission(Player p){

        ItemButton lanterButton = new ItemButton(seaLantern);
        ItemButton webButton = new ItemButton(web);
        ItemButton arrowButton = new ItemButton(arrow);
        ItemButton backgroundButton = new ItemButton(background);
        arrowButton.setDefaultAction(ClickAction ->{
            p.closeInventory();
        });

        for(int i = 0; i < menu.getInventory().getSize(); i++){
            menu.setButton(i,backgroundButton);
        }

        for (EntityMission entityMission : plugin.getHashMission()) {
            MakeItem missionItem = new MakeItem(entityMission.getIdItem(), entityMission.getDataItem());
            missionItem.addLore((ArrayList<String>) entityMission.getDescription());
            missionItem.setName(entityMission.getMissionName());
            ItemButton mission = new ItemButton(missionItem.build());
            setButton(entityMission.getSlot(),mission);
        }

        menu.setButton(40,arrowButton);
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

    public void openMenuMission(Player p){
        createInvMission(p);
        menu.show(p);
    }


}
