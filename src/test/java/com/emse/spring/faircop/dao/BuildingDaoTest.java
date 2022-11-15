package com.emse.spring.faircop.dao;



import com.emse.spring.faircop.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BuildingDaoTest {
    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindABuilding() {
        Building building = buildingDao.getReferenceById(-10L);
        Assertions.assertThat(building.getName()).isEqualTo("Building1");
    }

    @Test
    public void shouldFindBuildingHeaters() {
        List<Heater> result = buildingDao.findAllHeaters(-10L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "power", "heaterStatus")
                .containsExactly(Tuple.tuple(-10L, 2000L, HeaterStatus.ON), Tuple.tuple(-9L, null, HeaterStatus.ON));
    }

}