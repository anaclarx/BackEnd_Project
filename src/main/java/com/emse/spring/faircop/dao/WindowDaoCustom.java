package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Window;

import java.util.List;

public interface WindowDaoCustom {
    List<Window> findByOpenWindow(Long id);
}
