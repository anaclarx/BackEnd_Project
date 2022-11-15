package com.emse.spring.faircop.dto;

import com.emse.spring.faircop.model.Building;

public class BuildingDto {
    private Long id;
    private String name;

    public BuildingDto(){
    }

    public BuildingDto(Building building){
        this.id = building.getId();
        this.name = building.getName();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
