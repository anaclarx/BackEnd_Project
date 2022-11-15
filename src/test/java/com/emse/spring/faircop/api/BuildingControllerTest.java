package com.emse.spring.faircop.api;

import com.emse.spring.faircop.dao.*;
import com.emse.spring.faircop.dto.*;
import com.emse.spring.faircop.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
class BuildingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeaterDao heaterDao;
    @MockBean
    private WindowDao windowDao;
    @MockBean
    private BuildingDao buildingDao;
    @MockBean
    private RoomDao roomDao;

    private Building createBuilding(String name) {
        return new Building(name);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadBuildings() throws Exception {
        given(buildingDao.findAll()).willReturn(List.of(
                createBuilding("Building2"),
                createBuilding("Building3")
        ));

        mockMvc.perform(get("/api/buildings").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name")
                        .value(containsInAnyOrder("Building2", "Building3")));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldDeleteBuilding() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.of(createBuilding("Building2")));

        mockMvc.perform(delete("/api/buildings/999").with(csrf()))
                .andExpect(status().isOk());
    }

}