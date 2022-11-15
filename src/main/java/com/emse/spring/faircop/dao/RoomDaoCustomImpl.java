package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Window;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDaoCustomImpl implements RoomDaoCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Window> findRoomWindows(Long id){
        String jpql="select w from Window w where w.room.id=:id";
        return em.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void deleteRoom(Long id){
        String jpql = "delete from Room r where id = :id";
        em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }
}