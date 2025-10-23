package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTachePK;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class EmployeTacheService {

    public boolean create(EmployeTache et) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Réattacher les entités détachées
            if (et.getEmploye() != null && et.getEmploye().getId() > 0) {
                et.setEmploye(em.merge(et.getEmploye()));
            }
            if (et.getTache() != null && et.getTache().getId() > 0) {
                et.setTache(em.merge(et.getTache()));
            }

            em.persist(et);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean update(EmployeTache et) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(et);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean delete(int employeId, int tacheId) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            EmployeTachePK pk = new EmployeTachePK(employeId, tacheId);
            EmployeTache et = em.find(EmployeTache.class, pk);
            if (et != null) {
                em.remove(et);
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmployeTache> findAll() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.createQuery("SELECT et FROM EmployeTache et", EmployeTache.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}

