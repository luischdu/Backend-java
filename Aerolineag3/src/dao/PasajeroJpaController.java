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
import modelo.Equipaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pasajero;
import modelo.Reserva;

/**
 *
 * @author luischdu
 */
public class PasajeroJpaController implements Serializable {

    public PasajeroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pasajero pasajero) throws PreexistingEntityException, Exception {
        if (pasajero.getEquipajeList() == null) {
            pasajero.setEquipajeList(new ArrayList<Equipaje>());
        }
        if (pasajero.getReservaList() == null) {
            pasajero.setReservaList(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Equipaje> attachedEquipajeList = new ArrayList<Equipaje>();
            for (Equipaje equipajeListEquipajeToAttach : pasajero.getEquipajeList()) {
                equipajeListEquipajeToAttach = em.getReference(equipajeListEquipajeToAttach.getClass(), equipajeListEquipajeToAttach.getEquipajePK());
                attachedEquipajeList.add(equipajeListEquipajeToAttach);
            }
            pasajero.setEquipajeList(attachedEquipajeList);
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : pasajero.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getReservaPK());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            pasajero.setReservaList(attachedReservaList);
            em.persist(pasajero);
            for (Equipaje equipajeListEquipaje : pasajero.getEquipajeList()) {
                Pasajero oldPasajeroOfEquipajeListEquipaje = equipajeListEquipaje.getPasajero();
                equipajeListEquipaje.setPasajero(pasajero);
                equipajeListEquipaje = em.merge(equipajeListEquipaje);
                if (oldPasajeroOfEquipajeListEquipaje != null) {
                    oldPasajeroOfEquipajeListEquipaje.getEquipajeList().remove(equipajeListEquipaje);
                    oldPasajeroOfEquipajeListEquipaje = em.merge(oldPasajeroOfEquipajeListEquipaje);
                }
            }
            for (Reserva reservaListReserva : pasajero.getReservaList()) {
                Pasajero oldPasajeroOfReservaListReserva = reservaListReserva.getPasajero();
                reservaListReserva.setPasajero(pasajero);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldPasajeroOfReservaListReserva != null) {
                    oldPasajeroOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldPasajeroOfReservaListReserva = em.merge(oldPasajeroOfReservaListReserva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPasajero(pasajero.getIdPasajero()) != null) {
                throw new PreexistingEntityException("Pasajero " + pasajero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pasajero pasajero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero persistentPasajero = em.find(Pasajero.class, pasajero.getIdPasajero());
            List<Equipaje> equipajeListOld = persistentPasajero.getEquipajeList();
            List<Equipaje> equipajeListNew = pasajero.getEquipajeList();
            List<Reserva> reservaListOld = persistentPasajero.getReservaList();
            List<Reserva> reservaListNew = pasajero.getReservaList();
            List<String> illegalOrphanMessages = null;
            for (Equipaje equipajeListOldEquipaje : equipajeListOld) {
                if (!equipajeListNew.contains(equipajeListOldEquipaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipaje " + equipajeListOldEquipaje + " since its pasajero field is not nullable.");
                }
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its pasajero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Equipaje> attachedEquipajeListNew = new ArrayList<Equipaje>();
            for (Equipaje equipajeListNewEquipajeToAttach : equipajeListNew) {
                equipajeListNewEquipajeToAttach = em.getReference(equipajeListNewEquipajeToAttach.getClass(), equipajeListNewEquipajeToAttach.getEquipajePK());
                attachedEquipajeListNew.add(equipajeListNewEquipajeToAttach);
            }
            equipajeListNew = attachedEquipajeListNew;
            pasajero.setEquipajeList(equipajeListNew);
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getReservaPK());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            pasajero.setReservaList(reservaListNew);
            pasajero = em.merge(pasajero);
            for (Equipaje equipajeListNewEquipaje : equipajeListNew) {
                if (!equipajeListOld.contains(equipajeListNewEquipaje)) {
                    Pasajero oldPasajeroOfEquipajeListNewEquipaje = equipajeListNewEquipaje.getPasajero();
                    equipajeListNewEquipaje.setPasajero(pasajero);
                    equipajeListNewEquipaje = em.merge(equipajeListNewEquipaje);
                    if (oldPasajeroOfEquipajeListNewEquipaje != null && !oldPasajeroOfEquipajeListNewEquipaje.equals(pasajero)) {
                        oldPasajeroOfEquipajeListNewEquipaje.getEquipajeList().remove(equipajeListNewEquipaje);
                        oldPasajeroOfEquipajeListNewEquipaje = em.merge(oldPasajeroOfEquipajeListNewEquipaje);
                    }
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Pasajero oldPasajeroOfReservaListNewReserva = reservaListNewReserva.getPasajero();
                    reservaListNewReserva.setPasajero(pasajero);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldPasajeroOfReservaListNewReserva != null && !oldPasajeroOfReservaListNewReserva.equals(pasajero)) {
                        oldPasajeroOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldPasajeroOfReservaListNewReserva = em.merge(oldPasajeroOfReservaListNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pasajero.getIdPasajero();
                if (findPasajero(id) == null) {
                    throw new NonexistentEntityException("The pasajero with id " + id + " no longer exists.");
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
            Pasajero pasajero;
            try {
                pasajero = em.getReference(Pasajero.class, id);
                pasajero.getIdPasajero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pasajero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Equipaje> equipajeListOrphanCheck = pasajero.getEquipajeList();
            for (Equipaje equipajeListOrphanCheckEquipaje : equipajeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pasajero (" + pasajero + ") cannot be destroyed since the Equipaje " + equipajeListOrphanCheckEquipaje + " in its equipajeList field has a non-nullable pasajero field.");
            }
            List<Reserva> reservaListOrphanCheck = pasajero.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pasajero (" + pasajero + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable pasajero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pasajero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pasajero> findPasajeroEntities() {
        return findPasajeroEntities(true, -1, -1);
    }

    public List<Pasajero> findPasajeroEntities(int maxResults, int firstResult) {
        return findPasajeroEntities(false, maxResults, firstResult);
    }

    private List<Pasajero> findPasajeroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pasajero.class));
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

    public Pasajero findPasajero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pasajero.class, id);
        } finally {
            em.close();
        }
    }

    public int getPasajeroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pasajero> rt = cq.from(Pasajero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
