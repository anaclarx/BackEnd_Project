package com.emse.spring.faircop.api;

import com.emse.spring.faircop.dao.HeaterDao;
import com.emse.spring.faircop.dao.RoomDao;
import com.emse.spring.faircop.dto.HeaterDto;
import com.emse.spring.faircop.model.Heater;
import com.emse.spring.faircop.model.HeaterStatus;
import com.emse.spring.faircop.model.Room;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/heaters")
@Transactional

public class HeaterController {

    private final HeaterDao heaterDao;
    private final RoomDao roomDao;

    public HeaterController(HeaterDao heaterDao, RoomDao roomDao){
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
    }

    @GetMapping
    public List<HeaterDto> findAll() {
        return heaterDao.findAll().stream().map(HeaterDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public HeaterDto findById(@PathVariable Long id){return heaterDao.findById(id).map(HeaterDto::new).orElse(null);}

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        heaterDao.deleteById(id);
    }

    @PutMapping(path = "{id}/switch")
    public HeaterDto switchStatus(@PathVariable Long id){
        Heater heater = heaterDao.findById(id).orElseThrow(IllegalArgumentException::new);
        heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF : HeaterStatus.ON);
        return new HeaterDto(heater);
    }

    @PostMapping
    public HeaterDto create(@RequestBody HeaterDto dto){
        Room room = roomDao.getReferenceById(dto.getId());
        Heater heater = null;
        if(dto.getId() == null){
            heater =heaterDao.save(new Heater(dto.getName(),dto.getPower(), dto.getHeaterStatus(), room));
        }
        else{
            heater = heaterDao.getReferenceById(dto.getId());
            heater.setHeaterStatus(dto.getHeaterStatus());
        }
        return new HeaterDto(heater);
    }



}

