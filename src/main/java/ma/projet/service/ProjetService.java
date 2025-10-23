package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet projet) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(projet);
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

    @Override
    public boolean update(Projet projet) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(projet);
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

    @Override
    public boolean delete(Projet projet) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Projet p = em.find(Projet.class, projet.getId());
            if (p != null) {
                em.remove(p);
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

    @Override
    public Projet findById(int id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.find(Projet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Projet> findAll() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.createQuery("SELECT p FROM Projet p", Projet.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Méthode pour afficher la liste des tâches planifiées pour un projet
    public List<Tache> getTachesPlannifiees(int projetId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            String jpql = "SELECT t FROM Tache t WHERE t.projet.id = :projetId";
            return em.createQuery(jpql, Tache.class)
                    .setParameter("projetId", projetId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Méthode pour afficher la liste des tâches réalisées avec les dates réelles
    public List<Object[]> getTachesRealisees(int projetId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            String jpql = "SELECT t, et.dateDebutReelle, et.dateFinReelle " +
                         "FROM Tache t " +
                         "JOIN t.employeTaches et " +
                         "WHERE t.projet.id = :projetId AND et.dateFinReelle IS NOT NULL";
            return em.createQuery(jpql, Object[].class)
                    .setParameter("projetId", projetId)
                    .getResultList();
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

