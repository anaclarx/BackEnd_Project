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
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeaterController.class)
class HeaterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeaterDao heaterDao;
    @MockBean
    private RoomDao roomDao;

    private Heater createHeater(String name) {
        Building building = new Building();
        Room room = new Room(1, "S1", building, null, null);
        return new Heater( name, HeaterStatus.ON,room);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadHeaters() throws Exception {
        given(heaterDao.findAll()).willReturn(List.of(
                createHeater("Heater2"),
                createHeater("Heater3")
        ));

        mockMvc.perform(get("/api/heaters").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name")
                        .value(containsInAnyOrder("Heater2", "Heater3")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldSwitchHeater() throws Exception {
        Heater expectedHeater = createHeater("Heater2");
        Assertions.assertThat(expectedHeater.getHeaterStatus()).isEqualTo(HeaterStatus.ON);

        given(heaterDao.findById(999L)).willReturn(Optional.of(expectedHeater));

        mockMvc.perform(put("/api/heaters/999/switch").accept(APPLICATION_JSON).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Heater2"))
                .andExpect(jsonPath("$.heaterStatus").value("OFF"));
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    void shouldUpdateHeater() throws Exception {
//        Heater expectedHeater = createHeater("Heater2");
//        expectedHeater.setId(1L);
//        expectedHeater.setPower(100L);
//        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));
//
//        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
//        given(heaterDao.getReferenceById(anyLong())).willReturn(expectedHeater);
//
//        mockMvc.perform(post("/api/heaters")
//                        .with(csrf())
//                        .content(json)
//                        .accept(APPLICATION_JSON)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Heater2"))
//                .andExpect(jsonPath("$.power").value("100"))
//                .andExpect(jsonPath("$.id").value("1"));
//    }

}
