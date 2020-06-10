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
        for (EntityMissionAccept entityMissionPlayer : missionHashSet) {
            if(entityMission.getMissionName().equals(entityMissionPlayer.getMission().getMissionName())){
                String missionName = entityMission.getMissionName();
                return true;
            }
        }
        return false;
    }

    public void setMissionHashSet(HashSet<EntityMissionAccept> missionHashSet) {
        this.missionHashSet = missionHashSet;
    }


    public HashSet<EntityMissionAccept> getMissionHashSet() {
        return missionHashSet;
    }

    @Override
    public String toString() {
        return "EntityPlayer{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", missionHashSet=" + missionHashSet.toString() +
                '}';
    }
}
