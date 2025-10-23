package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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

    public boolean create(Tache o, int projetId) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Projet projet = em.find(Projet.class, projetId);
            if (projet != null) {
                o.setProjet(projet);
                em.persist(o);
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Tache tache = em.find(Tache.class, o.getId());
            if (tache != null) {
                em.remove(tache);
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
    public Tache findById(int id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.find(Tache.class, id);
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
    public List<Tache> findAll() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.createQuery("SELECT t FROM Tache t", Tache.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Méthode pour afficher les tâches dont le prix est supérieur à 1000 DH
    public List<Tache> getTachesPrixSuperieur(double prixMinimum) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            String jpql = "SELECT t FROM Tache t WHERE t.prix > :prixMinimum";
            return em.createQuery(jpql, Tache.class)
                    .setParameter("prixMinimum", prixMinimum)
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

    // Méthode pour afficher les tâches réalisées entre deux dates
    public List<Tache> getTachesRealisesEntreDates(Date dateDebut, Date dateFin) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            String jpql = "SELECT DISTINCT t FROM Tache t " +
                         "JOIN t.employeTaches et " +
                         "WHERE et.dateFinReelle BETWEEN :dateDebut AND :dateFin";
            return em.createQuery(jpql, Tache.class)
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
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

