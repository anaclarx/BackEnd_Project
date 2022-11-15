package com.emse.spring.faircop.dao;

import com.emse.spring.faircop.model.Heater;
import com.emse.spring.faircop.model.Window;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BuildingDaoCustomImpl implements BuildingDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Window> findAllWindows(Long id){
        String jpql ="select w from Window w where w.room.building.id = :id";
        return em.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .getResultList();
    }
    @Override
    public List<Heater> findAllHeaters(Long id){
        String jpql ="select h from Heater h where h.room.building.id = :id";
        return em.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .getResultList();
    }


    @Override
    public void deleteByBuildingId(Long building_id){

        String jpqlb = "delete from Building b where id=:id";
        em.createQuery(jpqlb)
                .setParameter("id", building_id)
                .executeUpdate();

    }
}
