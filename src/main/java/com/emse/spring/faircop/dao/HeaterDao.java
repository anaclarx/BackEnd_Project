package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Heater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeaterDao extends JpaRepository<Heater, Long> {

    @Query("select h from Heater h where h.name =: name")
    Heater findByName(@Param("name")String name);

    @Modifying
    @Query("delete from Heater h where h.room.id =: id")
    void deleteByRoom(Long id);

}
