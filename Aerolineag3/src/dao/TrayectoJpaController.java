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
import modelo.Ciudad;
import modelo.Trayecto;
import modelo.TrayectoPK;
import modelo.Vuelo;

/**
 *
 * @author luischdu
 */
public class TrayectoJpaController implements Serializable {

    public TrayectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trayecto trayecto) throws PreexistingEntityException, Exception {
        if (trayecto.getTrayectoPK() == null) {
            trayecto.setTrayectoPK(new TrayectoPK());
        }
        trayecto.getTrayectoPK().setVueloidVuelo(trayecto.getVuelo().getIdVuelo());
        trayecto.getTrayectoPK().setOrigen(trayecto.getCiudad().getIdCiudad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad = trayecto.getCiudad();
            if (ciudad != null) {
                ciudad = em.getReference(ciudad.getClass(), ciudad.getIdCiudad());
                trayecto.setCiudad(ciudad);
            }
            Vuelo vuelo = trayecto.getVuelo();
            if (vuelo != null) {
                vuelo = em.getReference(vuelo.getClass(), vuelo.getIdVuelo());
                trayecto.setVuelo(vuelo);
            }
            em.persist(trayecto);
            if (ciudad != null) {
                ciudad.getTrayectoList().add(trayecto);
                ciudad = em.merge(ciudad);
            }
            if (vuelo != null) {
                vuelo.getTrayectoList().add(trayecto);
                vuelo = em.merge(vuelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrayecto(trayecto.getTrayectoPK()) != null) {
                throw new PreexistingEntityException("Trayecto " + trayecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trayecto trayecto) throws NonexistentEntityException, Exception {
        trayecto.getTrayectoPK().setVueloidVuelo(trayecto.getVuelo().getIdVuelo());
        trayecto.getTrayectoPK().setOrigen(trayecto.getCiudad().getIdCiudad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trayecto persistentTrayecto = em.find(Trayecto.class, trayecto.getTrayectoPK());
            Ciudad ciudadOld = persistentTrayecto.getCiudad();
            Ciudad ciudadNew = trayecto.getCiudad();
            Vuelo vueloOld = persistentTrayecto.getVuelo();
            Vuelo vueloNew = trayecto.getVuelo();
            if (ciudadNew != null) {
                ciudadNew = em.getReference(ciudadNew.getClass(), ciudadNew.getIdCiudad());
                trayecto.setCiudad(ciudadNew);
            }
            if (vueloNew != null) {
                vueloNew = em.getReference(vueloNew.getClass(), vueloNew.getIdVuelo());
                trayecto.setVuelo(vueloNew);
            }
            trayecto = em.merge(trayecto);
            if (ciudadOld != null && !ciudadOld.equals(ciudadNew)) {
                ciudadOld.getTrayectoList().remove(trayecto);
                ciudadOld = em.merge(ciudadOld);
            }
            if (ciudadNew != null && !ciudadNew.equals(ciudadOld)) {
                ciudadNew.getTrayectoList().add(trayecto);
                ciudadNew = em.merge(ciudadNew);
            }
            if (vueloOld != null && !vueloOld.equals(vueloNew)) {
                vueloOld.getTrayectoList().remove(trayecto);
                vueloOld = em.merge(vueloOld);
            }
            if (vueloNew != null && !vueloNew.equals(vueloOld)) {
                vueloNew.getTrayectoList().add(trayecto);
                vueloNew = em.merge(vueloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TrayectoPK id = trayecto.getTrayectoPK();
                if (findTrayecto(id) == null) {
                    throw new NonexistentEntityException("The trayecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TrayectoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trayecto trayecto;
            try {
                trayecto = em.getReference(Trayecto.class, id);
                trayecto.getTrayectoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trayecto with id " + id + " no longer exists.", enfe);
            }
            Ciudad ciudad = trayecto.getCiudad();
            if (ciudad != null) {
                ciudad.getTrayectoList().remove(trayecto);
                ciudad = em.merge(ciudad);
            }
            Vuelo vuelo = trayecto.getVuelo();
            if (vuelo != null) {
                vuelo.getTrayectoList().remove(trayecto);
                vuelo = em.merge(vuelo);
            }
            em.remove(trayecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trayecto> findTrayectoEntities() {
        return findTrayectoEntities(true, -1, -1);
    }

    public List<Trayecto> findTrayectoEntities(int maxResults, int firstResult) {
        return findTrayectoEntities(false, maxResults, firstResult);
    }

    private List<Trayecto> findTrayectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trayecto.class));
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

    public Trayecto findTrayecto(TrayectoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trayecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrayectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trayecto> rt = cq.from(Trayecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
