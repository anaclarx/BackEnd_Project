package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Heater;

import java.util.List;

public interface HeaterDaoCustom {
    List<Heater> findHeatersByRoom(Long id);

    void deleteHeater(Long id);
}
