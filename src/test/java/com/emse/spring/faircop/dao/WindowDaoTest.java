package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Window;
import com.emse.spring.faircop.model.WindowStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class WindowDaoTest {

    @Autowired
    private WindowDao windowDao;

    @Autowired
    private RoomDao roomDao;
    @Test
    public void shouldFindAWindow(){
        Window window = windowDao.getReferenceById(-10L);
        Assertions.assertThat(window.getName()).isEqualTo("Window 1");
        Assertions.assertThat(window.getWindowStatus()).isEqualTo(WindowStatus.CLOSED);

    }


    @Test
    public void shouldFindRoomOpenWindows() {
        List<Window> result = windowDao.findByOpenWindow(-9L);
        Assertions.assertThat(result)
                .hasSize(1)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN));
    }

    @Test
    public void shouldNotFindRoomOpenWindows() {
        List<Window> result = windowDao.findByOpenWindow(-10L);
        Assertions.assertThat(result).isEmpty();
    }

}
