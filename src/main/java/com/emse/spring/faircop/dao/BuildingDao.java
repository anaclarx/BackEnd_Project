package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuildingDao extends JpaRepository<Building, Long>, BuildingDaoCustom {

    @Query("select b from Building b where b.name=:name")
    Building findByName(@Param("name") String name);
}
