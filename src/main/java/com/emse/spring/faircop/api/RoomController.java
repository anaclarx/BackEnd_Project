package com.emse.spring.faircop.api;

import com.emse.spring.faircop.dao.BuildingDao;
import com.emse.spring.faircop.dao.RoomDao;
import com.emse.spring.faircop.dto.RoomDto;
import com.emse.spring.faircop.dto.WindowDto;
import com.emse.spring.faircop.model.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController // (1)
@RequestMapping("/api/rooms") // (2)
@Transactional // (3)
public class RoomController {

    private final RoomDao roomDao;
    private final BuildingDao buildingDao;

    public RoomController(RoomDao roomDao, BuildingDao buildingDao){
        this.roomDao = roomDao;
        this.buildingDao = buildingDao;
    }

    @GetMapping
    public List<RoomDto> findAll(){
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id){
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        roomDao.deleteById(id);
    }

    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        Building building = buildingDao.getReferenceById(dto.getBuildingId());
        Room room = null;
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName(), building, dto.getCurrent_temperature(), dto.getTarget_temperature() ));
        } else {
            room = roomDao.getReferenceById(dto.getId());  // (9)
        }
        return new RoomDto(room);
    }

    @PutMapping(path = "/{id}")
    public RoomDto updateTargetTemperature(@PathVariable Long id, @RequestBody RoomDto dto) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        room.setTargetTemperature(dto.getTarget_temperature());

        return new RoomDto(room);
    }

    @GetMapping(path="/{id}/windows")
    public List<WindowDto> findRoomWindows(@PathVariable Long id) {
        List<Window> windows= roomDao.findRoomWindows(id);
        return windows.stream().map(WindowDto::new).collect(Collectors.toList());
    }

}
