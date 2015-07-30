package com.postal.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by brakhar on 6/12/2015.
 */

@Repository
public class StatisticDAOImpl implements StatisticDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Integer> getSetEditionYear() {
        List<Integer> result = entityManager.createNativeQuery("SELECT distinct year FROM STAMP stamp ORDER BY year ASC").getResultList();
        return result;
    }
}
