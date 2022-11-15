package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WindowDao extends JpaRepository<Window, Long>, WindowDaoCustom {

    @Query("select c from Window c where c.name=:name")
    Window findByName(@Param("name") String name);
    @Modifying
    @Query("delete from Window w where w.room.id = :roomID")
    void deleteByRoom(@Param("roomID") Long roomId);

}