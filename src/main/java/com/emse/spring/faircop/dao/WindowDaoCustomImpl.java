package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Window;
import com.emse.spring.faircop.model.WindowStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WindowDaoCustomImpl implements WindowDaoCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Window> findByOpenWindow(Long id){
        String jpql = "select w from Window w where w.room.id =:id and w.windowStatus=:status";
        return em.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .setParameter("status", WindowStatus.OPEN)
                .getResultList();
    }

}