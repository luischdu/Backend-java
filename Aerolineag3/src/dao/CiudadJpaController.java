/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Trayecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ciudad;

/**
 *
 * @author luischdu
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws PreexistingEntityException, Exception {
        if (ciudad.getTrayectoList() == null) {
            ciudad.setTrayectoList(new ArrayList<Trayecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Trayecto> attachedTrayectoList = new ArrayList<Trayecto>();
            for (Trayecto trayectoListTrayectoToAttach : ciudad.getTrayectoList()) {
                trayectoListTrayectoToAttach = em.getReference(trayectoListTrayectoToAttach.getClass(), trayectoListTrayectoToAttach.getTrayectoPK());
                attachedTrayectoList.add(trayectoListTrayectoToAttach);
            }
            ciudad.setTrayectoList(attachedTrayectoList);
            em.persist(ciudad);
            for (Trayecto trayectoListTrayecto : ciudad.getTrayectoList()) {
                Ciudad oldCiudadOfTrayectoListTrayecto = trayectoListTrayecto.getCiudad();
                trayectoListTrayecto.setCiudad(ciudad);
                trayectoListTrayecto = em.merge(trayectoListTrayecto);
                if (oldCiudadOfTrayectoListTrayecto != null) {
                    oldCiudadOfTrayectoListTrayecto.getTrayectoList().remove(trayectoListTrayecto);
                    oldCiudadOfTrayectoListTrayecto = em.merge(oldCiudadOfTrayectoListTrayecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCiudad(ciudad.getIdCiudad()) != null) {
                throw new PreexistingEntityException("Ciudad " + ciudad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getIdCiudad());
            List<Trayecto> trayectoListOld = persistentCiudad.getTrayectoList();
            List<Trayecto> trayectoListNew = ciudad.getTrayectoList();
            List<String> illegalOrphanMessages = null;
            for (Trayecto trayectoListOldTrayecto : trayectoListOld) {
                if (!trayectoListNew.contains(trayectoListOldTrayecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trayecto " + trayectoListOldTrayecto + " since its ciudad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trayecto> attachedTrayectoListNew = new ArrayList<Trayecto>();
            for (Trayecto trayectoListNewTrayectoToAttach : trayectoListNew) {
                trayectoListNewTrayectoToAttach = em.getReference(trayectoListNewTrayectoToAttach.getClass(), trayectoListNewTrayectoToAttach.getTrayectoPK());
                attachedTrayectoListNew.add(trayectoListNewTrayectoToAttach);
            }
            trayectoListNew = attachedTrayectoListNew;
            ciudad.setTrayectoList(trayectoListNew);
            ciudad = em.merge(ciudad);
            for (Trayecto trayectoListNewTrayecto : trayectoListNew) {
                if (!trayectoListOld.contains(trayectoListNewTrayecto)) {
                    Ciudad oldCiudadOfTrayectoListNewTrayecto = trayectoListNewTrayecto.getCiudad();
                    trayectoListNewTrayecto.setCiudad(ciudad);
                    trayectoListNewTrayecto = em.merge(trayectoListNewTrayecto);
                    if (oldCiudadOfTrayectoListNewTrayecto != null && !oldCiudadOfTrayectoListNewTrayecto.equals(ciudad)) {
                        oldCiudadOfTrayectoListNewTrayecto.getTrayectoList().remove(trayectoListNewTrayecto);
                        oldCiudadOfTrayectoListNewTrayecto = em.merge(oldCiudadOfTrayectoListNewTrayecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getIdCiudad();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getIdCiudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trayecto> trayectoListOrphanCheck = ciudad.getTrayectoList();
            for (Trayecto trayectoListOrphanCheckTrayecto : trayectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Trayecto " + trayectoListOrphanCheckTrayecto + " in its trayectoList field has a non-nullable ciudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
