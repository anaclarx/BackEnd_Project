package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Heater;
import com.emse.spring.faircop.model.Window;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingDaoCustom {
    List<Window> findAllWindows(Long id);
    List<Heater> findAllHeaters(Long id);
    void deleteByBuildingId(Long building_id);
}

