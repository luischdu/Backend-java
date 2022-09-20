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
import modelo.Pasajero;
import modelo.Reserva;
import modelo.ReservaPK;
import modelo.Vuelo;

/**
 *
 * @author luischdu
 */
public class ReservaJpaController implements Serializable {

    public ReservaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reserva reserva) throws PreexistingEntityException, Exception {
        if (reserva.getReservaPK() == null) {
            reserva.setReservaPK(new ReservaPK());
        }
        reserva.getReservaPK().setPasajeroidPasajero(reserva.getPasajero().getIdPasajero());
        reserva.getReservaPK().setVueloidVuelo(reserva.getVuelo().getIdVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero pasajero = reserva.getPasajero();
            if (pasajero != null) {
                pasajero = em.getReference(pasajero.getClass(), pasajero.getIdPasajero());
                reserva.setPasajero(pasajero);
            }
            Vuelo vuelo = reserva.getVuelo();
            if (vuelo != null) {
                vuelo = em.getReference(vuelo.getClass(), vuelo.getIdVuelo());
                reserva.setVuelo(vuelo);
            }
            em.persist(reserva);
            if (pasajero != null) {
                pasajero.getReservaList().add(reserva);
                pasajero = em.merge(pasajero);
            }
            if (vuelo != null) {
                vuelo.getReservaList().add(reserva);
                vuelo = em.merge(vuelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReserva(reserva.getReservaPK()) != null) {
                throw new PreexistingEntityException("Reserva " + reserva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reserva reserva) throws NonexistentEntityException, Exception {
        reserva.getReservaPK().setPasajeroidPasajero(reserva.getPasajero().getIdPasajero());
        reserva.getReservaPK().setVueloidVuelo(reserva.getVuelo().getIdVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva persistentReserva = em.find(Reserva.class, reserva.getReservaPK());
            Pasajero pasajeroOld = persistentReserva.getPasajero();
            Pasajero pasajeroNew = reserva.getPasajero();
            Vuelo vueloOld = persistentReserva.getVuelo();
            Vuelo vueloNew = reserva.getVuelo();
            if (pasajeroNew != null) {
                pasajeroNew = em.getReference(pasajeroNew.getClass(), pasajeroNew.getIdPasajero());
                reserva.setPasajero(pasajeroNew);
            }
            if (vueloNew != null) {
                vueloNew = em.getReference(vueloNew.getClass(), vueloNew.getIdVuelo());
                reserva.setVuelo(vueloNew);
            }
            reserva = em.merge(reserva);
            if (pasajeroOld != null && !pasajeroOld.equals(pasajeroNew)) {
                pasajeroOld.getReservaList().remove(reserva);
                pasajeroOld = em.merge(pasajeroOld);
            }
            if (pasajeroNew != null && !pasajeroNew.equals(pasajeroOld)) {
                pasajeroNew.getReservaList().add(reserva);
                pasajeroNew = em.merge(pasajeroNew);
            }
            if (vueloOld != null && !vueloOld.equals(vueloNew)) {
                vueloOld.getReservaList().remove(reserva);
                vueloOld = em.merge(vueloOld);
            }
            if (vueloNew != null && !vueloNew.equals(vueloOld)) {
                vueloNew.getReservaList().add(reserva);
                vueloNew = em.merge(vueloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ReservaPK id = reserva.getReservaPK();
                if (findReserva(id) == null) {
                    throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ReservaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva reserva;
            try {
                reserva = em.getReference(Reserva.class, id);
                reserva.getReservaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.", enfe);
            }
            Pasajero pasajero = reserva.getPasajero();
            if (pasajero != null) {
                pasajero.getReservaList().remove(reserva);
                pasajero = em.merge(pasajero);
            }
            Vuelo vuelo = reserva.getVuelo();
            if (vuelo != null) {
                vuelo.getReservaList().remove(reserva);
                vuelo = em.merge(vuelo);
            }
            em.remove(reserva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reserva> findReservaEntities() {
        return findReservaEntities(true, -1, -1);
    }

    public List<Reserva> findReservaEntities(int maxResults, int firstResult) {
        return findReservaEntities(false, maxResults, firstResult);
    }

    private List<Reserva> findReservaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
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

    public Reserva findReserva(ReservaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reserva> rt = cq.from(Reserva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
