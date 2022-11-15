package com.emse.spring.faircop.dto;

import com.emse.spring.faircop.model.Heater;
import com.emse.spring.faircop.model.HeaterStatus;

import java.util.List;

public class HeaterDto {
    private Long id;
    private String name;
    private HeaterStatus heaterStatus;
    private Long power;
    private String roomName;
    private Long roomId;

    public HeaterDto(List<Heater> heaters){}

    public HeaterDto(Heater heater){
        this.id = heater.getId();
        this.name = heater.getName();
        this.heaterStatus = heater.getHeaterStatus();
        this.power = heater.getPower();
        this.roomId = heater.getRoom().getId();
        this.roomName = heater.getRoom().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

}
