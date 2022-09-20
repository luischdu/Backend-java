/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Avion;
import modelo.AvionPK;
import modelo.Vuelo;

/**
 *
 * @author luischdu
 */
public class AvionJpaController implements Serializable {

    public AvionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avion avion) throws PreexistingEntityException, Exception {
        if (avion.getAvionPK() == null) {
            avion.setAvionPK(new AvionPK());
        }
        avion.getAvionPK().setVueloidVuelo(avion.getVuelo().getIdVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelo vuelo = avion.getVuelo();
            if (vuelo != null) {
                vuelo = em.getReference(vuelo.getClass(), vuelo.getIdVuelo());
                avion.setVuelo(vuelo);
            }
            em.persist(avion);
            if (vuelo != null) {
                vuelo.getAvionList().add(avion);
                vuelo = em.merge(vuelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvion(avion.getAvionPK()) != null) {
                throw new PreexistingEntityException("Avion " + avion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avion avion) throws NonexistentEntityException, Exception {
        avion.getAvionPK().setVueloidVuelo(avion.getVuelo().getIdVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avion persistentAvion = em.find(Avion.class, avion.getAvionPK());
            Vuelo vueloOld = persistentAvion.getVuelo();
            Vuelo vueloNew = avion.getVuelo();
            if (vueloNew != null) {
                vueloNew = em.getReference(vueloNew.getClass(), vueloNew.getIdVuelo());
                avion.setVuelo(vueloNew);
            }
            avion = em.merge(avion);
            if (vueloOld != null && !vueloOld.equals(vueloNew)) {
                vueloOld.getAvionList().remove(avion);
                vueloOld = em.merge(vueloOld);
            }
            if (vueloNew != null && !vueloNew.equals(vueloOld)) {
                vueloNew.getAvionList().add(avion);
                vueloNew = em.merge(vueloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AvionPK id = avion.getAvionPK();
                if (findAvion(id) == null) {
                    throw new NonexistentEntityException("The avion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AvionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avion avion;
            try {
                avion = em.getReference(Avion.class, id);
                avion.getAvionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avion with id " + id + " no longer exists.", enfe);
            }
            Vuelo vuelo = avion.getVuelo();
            if (vuelo != null) {
                vuelo.getAvionList().remove(avion);
                vuelo = em.merge(vuelo);
            }
            em.remove(avion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avion> findAvionEntities() {
        return findAvionEntities(true, -1, -1);
    }

    public List<Avion> findAvionEntities(int maxResults, int firstResult) {
        return findAvionEntities(false, maxResults, firstResult);
    }

    private List<Avion> findAvionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Avion findAvion(AvionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avion> rt = cq.from(Avion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
