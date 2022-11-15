package com.emse.spring.faircop.api;

import com.emse.spring.faircop.dao.*;
import com.emse.spring.faircop.dto.*;
import com.emse.spring.faircop.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {
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

    private Room createRoom(String name) {
        Building building = new Building();
        return new Room(1, name, building, 0.0, 10.0);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadRooms() throws Exception {
        given(roomDao.findAll()).willReturn(List.of(
                createRoom("Room2"),
                createRoom("Room3")
        ));

        mockMvc.perform(get("/api/rooms").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name")
                        .value(containsInAnyOrder("Room2", "Room3")));
    }


}