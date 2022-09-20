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
import modelo.Empleado;
import modelo.Itinireario;
import modelo.ItinirearioPK;
import modelo.Vuelo;

/**
 *
 * @author luischdu
 */
public class ItinirearioJpaController implements Serializable {

    public ItinirearioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itinireario itinireario) throws PreexistingEntityException, Exception {
        if (itinireario.getItinirearioPK() == null) {
            itinireario.setItinirearioPK(new ItinirearioPK());
        }
        itinireario.getItinirearioPK().setEmpleadoidEmpleado(itinireario.getEmpleado().getIdEmpleado());
        itinireario.getItinirearioPK().setVueloidVuelo(itinireario.getVuelo().getIdVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = itinireario.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getIdEmpleado());
                itinireario.setEmpleado(empleado);
            }
            Vuelo vuelo = itinireario.getVuelo();
            if (vuelo != null) {
                vuelo = em.getReference(vuelo.getClass(), vuelo.getIdVuelo());
                itinireario.setVuelo(vuelo);
            }
            em.persist(itinireario);
            if (empleado != null) {
                empleado.getItinirearioList().add(itinireario);
                empleado = em.merge(empleado);
            }
            if (vuelo != null) {
                vuelo.getItinirearioList().add(itinireario);
                vuelo = em.merge(vuelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItinireario(itinireario.getItinirearioPK()) != null) {
                throw new PreexistingEntityException("Itinireario " + itinireario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itinireario itinireario) throws NonexistentEntityException, Exception {
        itinireario.getItinirearioPK().setEmpleadoidEmpleado(itinireario.getEmpleado().getIdEmpleado());
        itinireario.getItinirearioPK().setVueloidVuelo(itinireario.getVuelo().getIdVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itinireario persistentItinireario = em.find(Itinireario.class, itinireario.getItinirearioPK());
            Empleado empleadoOld = persistentItinireario.getEmpleado();
            Empleado empleadoNew = itinireario.getEmpleado();
            Vuelo vueloOld = persistentItinireario.getVuelo();
            Vuelo vueloNew = itinireario.getVuelo();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getIdEmpleado());
                itinireario.setEmpleado(empleadoNew);
            }
            if (vueloNew != null) {
                vueloNew = em.getReference(vueloNew.getClass(), vueloNew.getIdVuelo());
                itinireario.setVuelo(vueloNew);
            }
            itinireario = em.merge(itinireario);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getItinirearioList().remove(itinireario);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getItinirearioList().add(itinireario);
                empleadoNew = em.merge(empleadoNew);
            }
            if (vueloOld != null && !vueloOld.equals(vueloNew)) {
                vueloOld.getItinirearioList().remove(itinireario);
                vueloOld = em.merge(vueloOld);
            }
            if (vueloNew != null && !vueloNew.equals(vueloOld)) {
                vueloNew.getItinirearioList().add(itinireario);
                vueloNew = em.merge(vueloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ItinirearioPK id = itinireario.getItinirearioPK();
                if (findItinireario(id) == null) {
                    throw new NonexistentEntityException("The itinireario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItinirearioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itinireario itinireario;
            try {
                itinireario = em.getReference(Itinireario.class, id);
                itinireario.getItinirearioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itinireario with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = itinireario.getEmpleado();
            if (empleado != null) {
                empleado.getItinirearioList().remove(itinireario);
                empleado = em.merge(empleado);
            }
            Vuelo vuelo = itinireario.getVuelo();
            if (vuelo != null) {
                vuelo.getItinirearioList().remove(itinireario);
                vuelo = em.merge(vuelo);
            }
            em.remove(itinireario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itinireario> findItinirearioEntities() {
        return findItinirearioEntities(true, -1, -1);
    }

    public List<Itinireario> findItinirearioEntities(int maxResults, int firstResult) {
        return findItinirearioEntities(false, maxResults, firstResult);
    }

    private List<Itinireario> findItinirearioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itinireario.class));
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

    public Itinireario findItinireario(ItinirearioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itinireario.class, id);
        } finally {
            em.close();
        }
    }

    public int getItinirearioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itinireario> rt = cq.from(Itinireario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
