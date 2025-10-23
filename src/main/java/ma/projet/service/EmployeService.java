package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(o);
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
    public boolean update(Employe o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(o);
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
    public boolean delete(Employe o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Employe employe = em.find(Employe.class, o.getId());
            if (employe != null) {
                em.remove(employe);
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
    public Employe findById(int id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.find(Employe.class, id);
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
    public List<Employe> findAll() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Méthode pour afficher la liste des tâches réalisées par un employé
    public List<Tache> getTachesRealisees(int employeId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            String jpql = "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :employeId";
            return em.createQuery(jpql, Tache.class)
                    .setParameter("employeId", employeId)
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

    // Méthode pour afficher la liste des projets gérés par un employé
    public List<Projet> getProjetsGeres(int employeId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            String jpql = "SELECT DISTINCT t.projet FROM Tache t " +
                         "JOIN t.employeTaches et " +
                         "WHERE et.employe.id = :employeId";
            return em.createQuery(jpql, Projet.class)
                    .setParameter("employeId", employeId)
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

