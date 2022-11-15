package com.emse.spring.faircop.api;


import com.emse.spring.faircop.dao.RoomDao;
import com.emse.spring.faircop.dao.WindowDao;
import com.emse.spring.faircop.model.Building;
import com.emse.spring.faircop.model.Room;
import com.emse.spring.faircop.model.Window;
import com.emse.spring.faircop.model.WindowStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
//@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WindowDao windowDao;

    @MockBean
    private RoomDao roomDao;
    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldLoadAWindowAndReturnNullIfNotFound() throws Exception {
        given(windowDao.findById(999L)).willReturn(Optional.empty());
        mockMvc.perform(get("/api/windows/999").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldSwitchWindow() throws Exception {
        Window expectedWindow = createWindow("Window1");
        Assertions.assertThat(expectedWindow.getWindowStatus()).isEqualTo(WindowStatus.OPEN);

        given(windowDao.findById(999L)).willReturn(Optional.of(expectedWindow));

        mockMvc.perform(put("/api/windows/999/switch").accept(APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Window1"))
                .andExpect(jsonPath("$.windowStatus").value("CLOSED"));
    }
    private Window createWindow(String s) {
        Building building = new Building("Building1");
        Room room= new Room( 1, "Room 1", building);
        Window window = new Window (s, WindowStatus.OPEN, room);
        return window;
    }
}