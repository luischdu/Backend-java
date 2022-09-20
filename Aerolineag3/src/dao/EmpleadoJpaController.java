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
import modelo.Itinireario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Empleado;

/**
 *
 * @author luischdu
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getItinirearioList() == null) {
            empleado.setItinirearioList(new ArrayList<Itinireario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Itinireario> attachedItinirearioList = new ArrayList<Itinireario>();
            for (Itinireario itinirearioListItinirearioToAttach : empleado.getItinirearioList()) {
                itinirearioListItinirearioToAttach = em.getReference(itinirearioListItinirearioToAttach.getClass(), itinirearioListItinirearioToAttach.getItinirearioPK());
                attachedItinirearioList.add(itinirearioListItinirearioToAttach);
            }
            empleado.setItinirearioList(attachedItinirearioList);
            em.persist(empleado);
            for (Itinireario itinirearioListItinireario : empleado.getItinirearioList()) {
                Empleado oldEmpleadoOfItinirearioListItinireario = itinirearioListItinireario.getEmpleado();
                itinirearioListItinireario.setEmpleado(empleado);
                itinirearioListItinireario = em.merge(itinirearioListItinireario);
                if (oldEmpleadoOfItinirearioListItinireario != null) {
                    oldEmpleadoOfItinirearioListItinireario.getItinirearioList().remove(itinirearioListItinireario);
                    oldEmpleadoOfItinirearioListItinireario = em.merge(oldEmpleadoOfItinirearioListItinireario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            List<Itinireario> itinirearioListOld = persistentEmpleado.getItinirearioList();
            List<Itinireario> itinirearioListNew = empleado.getItinirearioList();
            List<String> illegalOrphanMessages = null;
            for (Itinireario itinirearioListOldItinireario : itinirearioListOld) {
                if (!itinirearioListNew.contains(itinirearioListOldItinireario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Itinireario " + itinirearioListOldItinireario + " since its empleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Itinireario> attachedItinirearioListNew = new ArrayList<Itinireario>();
            for (Itinireario itinirearioListNewItinirearioToAttach : itinirearioListNew) {
                itinirearioListNewItinirearioToAttach = em.getReference(itinirearioListNewItinirearioToAttach.getClass(), itinirearioListNewItinirearioToAttach.getItinirearioPK());
                attachedItinirearioListNew.add(itinirearioListNewItinirearioToAttach);
            }
            itinirearioListNew = attachedItinirearioListNew;
            empleado.setItinirearioList(itinirearioListNew);
            empleado = em.merge(empleado);
            for (Itinireario itinirearioListNewItinireario : itinirearioListNew) {
                if (!itinirearioListOld.contains(itinirearioListNewItinireario)) {
                    Empleado oldEmpleadoOfItinirearioListNewItinireario = itinirearioListNewItinireario.getEmpleado();
                    itinirearioListNewItinireario.setEmpleado(empleado);
                    itinirearioListNewItinireario = em.merge(itinirearioListNewItinireario);
                    if (oldEmpleadoOfItinirearioListNewItinireario != null && !oldEmpleadoOfItinirearioListNewItinireario.equals(empleado)) {
                        oldEmpleadoOfItinirearioListNewItinireario.getItinirearioList().remove(itinirearioListNewItinireario);
                        oldEmpleadoOfItinirearioListNewItinireario = em.merge(oldEmpleadoOfItinirearioListNewItinireario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Itinireario> itinirearioListOrphanCheck = empleado.getItinirearioList();
            for (Itinireario itinirearioListOrphanCheckItinireario : itinirearioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Itinireario " + itinirearioListOrphanCheckItinireario + " in its itinirearioList field has a non-nullable empleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
