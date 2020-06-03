package com.arthuramorim.entitys;

import java.util.HashSet;
import java.util.UUID;

public class EntityPlayer {

    private UUID uuid;
    private String name;
    private HashSet<EntityMissionAccept> missionHashSet;

    public EntityPlayer(){
        
    }

    public EntityPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setMissionHashSet(HashSet<EntityMissionAccept> missionHashSet) {
        this.missionHashSet = missionHashSet;
    }


    public HashSet<EntityMissionAccept> getMissionHashSet() {
        return missionHashSet;
    }
}
