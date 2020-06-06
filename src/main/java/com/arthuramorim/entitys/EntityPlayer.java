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

    public boolean hasMission(EntityMission entityMission){
        Integer contador = 0;
        boolean hasMission = false;
        for (EntityMissionAccept entityMissionAccept : missionHashSet) {
            if(entityMissionAccept.getMission().getMissionName().equals(entityMission.getMissionName())){
                hasMission = true;
            }
        }
        return hasMission;
    }

    public void setMissionHashSet(HashSet<EntityMissionAccept> missionHashSet) {
        this.missionHashSet = missionHashSet;
    }


    public HashSet<EntityMissionAccept> getMissionHashSet() {
        return missionHashSet;
    }
}
