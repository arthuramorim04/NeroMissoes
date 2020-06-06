package com.arthuramorim.entitys;

import java.util.List;

public class EntityMission {

    private String missionName;
    private String type;
    private Integer requirement;
    private Integer reward;
    private Integer idItem;
    private Integer slot;
    private Byte dataItem;
    private Integer quantity;
    private List<String> description;

    public EntityMission(String missionName, String type, Integer requirement, Integer reward, Integer idItem, Byte dataItem, Integer quantity, Integer slot,List<String> description) {
        this.missionName = missionName;
        this.type = type;
        this.requirement = requirement;
        this.reward = reward;
        this.idItem = idItem;
        this.dataItem = dataItem;
        this.quantity = quantity;
        this.slot = slot;
        this.description = description;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getType() {
        return type;
    }

    public Integer getRequirement() {
        return requirement;
    }

    public Integer getReward() {
        return reward;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public Byte getDataItem() {
        return dataItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getSlot() {
        return slot;
    }

    public List<String> getDescription() {
        return description;
    }


    public Boolean verifyMission(Integer quantity){
        if(quantity >= this.quantity){
            return true;
        }else{
            return false;
        }
    }

}
