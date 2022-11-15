package com.emse.spring.faircop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BUILDING")
public class Building {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "building")
    private List<Room> listOfRooms;

    public Building(String name){
        this.name = name;
    }

    public Building(){}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Room> getRooms(){
        return listOfRooms;
    }

    public void setRooms(List<Room> listOfRooms){
        this.listOfRooms = listOfRooms;
    }
}
