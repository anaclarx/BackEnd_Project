package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Window;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomDaoCustom {
    List<Window> findRoomWindows(Long id);

    void deleteRoom(Long room_id);
}