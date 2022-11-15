package com.emse.spring.faircop.model;

import javax.persistence.*;

@Entity
@Table(name = "HEATER")
public class Heater {
    // (3)
    @Id
    @GeneratedValue
    private Long id;

    // (4)
    @Column(nullable=false, length=255)
    private String name;

    @ManyToOne(optional = false)
    private Room room;

    @Column(nullable = true)
    private Long power;

    // (5)
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private HeaterStatus heaterStatus;

    public Heater() {
    }

    public Heater(String name, HeaterStatus status, Room room){
        this.heaterStatus = status;
        this.name = name;
        this.room = room;
    }

    public Heater(String name, Long power, HeaterStatus status, Room room) {
        this.heaterStatus = status;
        this.name = name;
        this.room = room;
        this.power = power;
    }

    public Long getId() {
        return this.id;
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

    public Room getRoom(){
        return room;
    }

    public void setRoom(Room room){
        this.room = room;
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
}
