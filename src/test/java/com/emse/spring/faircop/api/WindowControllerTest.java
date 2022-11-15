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

@WebMvcTest(WindowController.class)
class WindowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WindowDao windowDao;
    @MockBean
    private RoomDao roomDao;

    private Window createWindow(String s) {
        Building building = new Building("Building1");
        Room room= new Room( 1, "Room 1", building);
        Window window = new Window (s, WindowStatus.OPEN, room);
        return window;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadWindows() throws Exception {
        given(windowDao.findAll()).willReturn(List.of(
                createWindow("Window2"),
                createWindow("Window3")
        ));
        mockMvc.perform(get("/api/windows").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name")
                        .value(containsInAnyOrder("Window2", "Window3")));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldSwitchWindow() throws Exception {
        Window expectedWindow = createWindow("Window2");
        Assertions.assertThat(expectedWindow.getWindowStatus()).isEqualTo(WindowStatus.OPEN);

        given(windowDao.findById(999L)).willReturn(Optional.of(expectedWindow));

        mockMvc.perform(put("/api/windows/999/switch").accept(APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Window2"))
                .andExpect(jsonPath("$.windowStatus").value("CLOSED"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldUpdateWindow() throws Exception {
        Window expectedWindow = createWindow("Window2");
        expectedWindow.setId(1L);
        String json = objectMapper.writeValueAsString(new WindowDto(expectedWindow));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedWindow.getRoom());
        given(windowDao.getReferenceById(anyLong())).willReturn(expectedWindow);

        mockMvc.perform(post("/api/windows")
                        .with(csrf())
                        .content(json)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Window2"))
                .andExpect(jsonPath("$.id").value("1"));
    }



}