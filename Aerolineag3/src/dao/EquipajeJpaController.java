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
import modelo.Equipaje;
import modelo.EquipajePK;
import modelo.Pasajero;

/**
 *
 * @author luischdu
 */
public class EquipajeJpaController implements Serializable {

    public EquipajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipaje equipaje) throws PreexistingEntityException, Exception {
        if (equipaje.getEquipajePK() == null) {
            equipaje.setEquipajePK(new EquipajePK());
        }
        equipaje.getEquipajePK().setPasajeroidPasajero(equipaje.getPasajero().getIdPasajero());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero pasajero = equipaje.getPasajero();
            if (pasajero != null) {
                pasajero = em.getReference(pasajero.getClass(), pasajero.getIdPasajero());
                equipaje.setPasajero(pasajero);
            }
            em.persist(equipaje);
            if (pasajero != null) {
                pasajero.getEquipajeList().add(equipaje);
                pasajero = em.merge(pasajero);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipaje(equipaje.getEquipajePK()) != null) {
                throw new PreexistingEntityException("Equipaje " + equipaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipaje equipaje) throws NonexistentEntityException, Exception {
        equipaje.getEquipajePK().setPasajeroidPasajero(equipaje.getPasajero().getIdPasajero());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipaje persistentEquipaje = em.find(Equipaje.class, equipaje.getEquipajePK());
            Pasajero pasajeroOld = persistentEquipaje.getPasajero();
            Pasajero pasajeroNew = equipaje.getPasajero();
            if (pasajeroNew != null) {
                pasajeroNew = em.getReference(pasajeroNew.getClass(), pasajeroNew.getIdPasajero());
                equipaje.setPasajero(pasajeroNew);
            }
            equipaje = em.merge(equipaje);
            if (pasajeroOld != null && !pasajeroOld.equals(pasajeroNew)) {
                pasajeroOld.getEquipajeList().remove(equipaje);
                pasajeroOld = em.merge(pasajeroOld);
            }
            if (pasajeroNew != null && !pasajeroNew.equals(pasajeroOld)) {
                pasajeroNew.getEquipajeList().add(equipaje);
                pasajeroNew = em.merge(pasajeroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EquipajePK id = equipaje.getEquipajePK();
                if (findEquipaje(id) == null) {
                    throw new NonexistentEntityException("The equipaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EquipajePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipaje equipaje;
            try {
                equipaje = em.getReference(Equipaje.class, id);
                equipaje.getEquipajePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipaje with id " + id + " no longer exists.", enfe);
            }
            Pasajero pasajero = equipaje.getPasajero();
            if (pasajero != null) {
                pasajero.getEquipajeList().remove(equipaje);
                pasajero = em.merge(pasajero);
            }
            em.remove(equipaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipaje> findEquipajeEntities() {
        return findEquipajeEntities(true, -1, -1);
    }

    public List<Equipaje> findEquipajeEntities(int maxResults, int firstResult) {
        return findEquipajeEntities(false, maxResults, firstResult);
    }

    private List<Equipaje> findEquipajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipaje.class));
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

    public Equipaje findEquipaje(EquipajePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipaje> rt = cq.from(Equipaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
