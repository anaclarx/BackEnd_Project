package com.emse.spring.faircop.api;

import com.emse.spring.faircop.dao.BuildingDao;
import com.emse.spring.faircop.dto.BuildingDto;
import com.emse.spring.faircop.dto.HeaterDto;
import com.emse.spring.faircop.dto.WindowDto;
import com.emse.spring.faircop.model.Building;
import com.emse.spring.faircop.model.Heater;
import com.emse.spring.faircop.model.Window;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {

    private final BuildingDao buildingDao;

    public BuildingController(BuildingDao buildingDao){this.buildingDao=buildingDao;}

    @GetMapping
    public List<BuildingDto> findAll(){
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id){
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @PostMapping // (8)
    public BuildingDto create(@RequestBody BuildingDto dto) {
        // On creation id is not defined
        Building building = null;
        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getName()));
        } else {
            building = buildingDao.getReferenceById(dto.getId());  // (9)
        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{building_id}")
    public void delete(@PathVariable Long building_id) {
        buildingDao.deleteByBuildingId(building_id);
    }

}
