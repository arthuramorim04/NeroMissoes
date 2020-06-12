package com.arthuramorim.entitys;

import java.util.concurrent.TimeUnit;

public class EntityMissionAccept {

    private EntityMission mission;
    private Integer progress;
    private long timeAcceptMission;
    private long timeExpiredMission;
    private long remainingTime;

    public EntityMission getMission() {
        return mission;
    }

    public void setMission(EntityMission mission) {
        this.mission = mission;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }


    public void addTimeExpired(){
        if(this.getMission().getExpired() > 0){
            long timeAcceptMission = System.currentTimeMillis();
            long timeExpiredMission = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(this.getMission().getExpired());

            this.setTimeAcceptMission(timeAcceptMission);
            this.setTimeExpiredMission(timeExpiredMission);
        }else{
            this.setTimeExpiredMission(0);
        }
    }

    public void calcReaminingTime(){
       long remainingMill =  this.getTimeExpiredMission() - System.currentTimeMillis();

       this.setRemainingTimeInMinute(TimeUnit.MILLISECONDS.toMinutes(remainingMill));
    }

    public boolean missionExpired(){
        if(this.getMission().getExpired() == 0) {
            return false;
        }else{
            if(this.getTimeExpiredMission() <= System.currentTimeMillis()){
                return true;
            }else{
                return false;
            }
        }
    }

    public void setRemainingTimeInMinute(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    public long getTimeAcceptMission() {
        return timeAcceptMission;
    }

    public long getTimeExpiredMission() {
        return timeExpiredMission;
    }

    public long getRemainingTime() {
        calcReaminingTime();
        return remainingTime;
    }

    public void setTimeAcceptMission(long timeAcceptMission) {
        this.timeAcceptMission = timeAcceptMission;
    }

    public void setTimeExpiredMission(long expired) {
        this.timeExpiredMission = expired;
    }
}
