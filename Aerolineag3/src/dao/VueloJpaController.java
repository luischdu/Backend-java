/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
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
import modelo.Itinireario;
import modelo.Reserva;
import modelo.Avion;
import modelo.Vuelo;

/**
 *
 * @author luischdu
 */
public class VueloJpaController implements Serializable {

    public VueloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vuelo vuelo) {
        if (vuelo.getTrayectoList() == null) {
            vuelo.setTrayectoList(new ArrayList<Trayecto>());
        }
        if (vuelo.getItinirearioList() == null) {
            vuelo.setItinirearioList(new ArrayList<Itinireario>());
        }
        if (vuelo.getReservaList() == null) {
            vuelo.setReservaList(new ArrayList<Reserva>());
        }
        if (vuelo.getAvionList() == null) {
            vuelo.setAvionList(new ArrayList<Avion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Trayecto> attachedTrayectoList = new ArrayList<Trayecto>();
            for (Trayecto trayectoListTrayectoToAttach : vuelo.getTrayectoList()) {
                trayectoListTrayectoToAttach = em.getReference(trayectoListTrayectoToAttach.getClass(), trayectoListTrayectoToAttach.getTrayectoPK());
                attachedTrayectoList.add(trayectoListTrayectoToAttach);
            }
            vuelo.setTrayectoList(attachedTrayectoList);
            List<Itinireario> attachedItinirearioList = new ArrayList<Itinireario>();
            for (Itinireario itinirearioListItinirearioToAttach : vuelo.getItinirearioList()) {
                itinirearioListItinirearioToAttach = em.getReference(itinirearioListItinirearioToAttach.getClass(), itinirearioListItinirearioToAttach.getItinirearioPK());
                attachedItinirearioList.add(itinirearioListItinirearioToAttach);
            }
            vuelo.setItinirearioList(attachedItinirearioList);
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : vuelo.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getReservaPK());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            vuelo.setReservaList(attachedReservaList);
            List<Avion> attachedAvionList = new ArrayList<Avion>();
            for (Avion avionListAvionToAttach : vuelo.getAvionList()) {
                avionListAvionToAttach = em.getReference(avionListAvionToAttach.getClass(), avionListAvionToAttach.getAvionPK());
                attachedAvionList.add(avionListAvionToAttach);
            }
            vuelo.setAvionList(attachedAvionList);
            em.persist(vuelo);
            for (Trayecto trayectoListTrayecto : vuelo.getTrayectoList()) {
                Vuelo oldVueloOfTrayectoListTrayecto = trayectoListTrayecto.getVuelo();
                trayectoListTrayecto.setVuelo(vuelo);
                trayectoListTrayecto = em.merge(trayectoListTrayecto);
                if (oldVueloOfTrayectoListTrayecto != null) {
                    oldVueloOfTrayectoListTrayecto.getTrayectoList().remove(trayectoListTrayecto);
                    oldVueloOfTrayectoListTrayecto = em.merge(oldVueloOfTrayectoListTrayecto);
                }
            }
            for (Itinireario itinirearioListItinireario : vuelo.getItinirearioList()) {
                Vuelo oldVueloOfItinirearioListItinireario = itinirearioListItinireario.getVuelo();
                itinirearioListItinireario.setVuelo(vuelo);
                itinirearioListItinireario = em.merge(itinirearioListItinireario);
                if (oldVueloOfItinirearioListItinireario != null) {
                    oldVueloOfItinirearioListItinireario.getItinirearioList().remove(itinirearioListItinireario);
                    oldVueloOfItinirearioListItinireario = em.merge(oldVueloOfItinirearioListItinireario);
                }
            }
            for (Reserva reservaListReserva : vuelo.getReservaList()) {
                Vuelo oldVueloOfReservaListReserva = reservaListReserva.getVuelo();
                reservaListReserva.setVuelo(vuelo);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldVueloOfReservaListReserva != null) {
                    oldVueloOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldVueloOfReservaListReserva = em.merge(oldVueloOfReservaListReserva);
                }
            }
            for (Avion avionListAvion : vuelo.getAvionList()) {
                Vuelo oldVueloOfAvionListAvion = avionListAvion.getVuelo();
                avionListAvion.setVuelo(vuelo);
                avionListAvion = em.merge(avionListAvion);
                if (oldVueloOfAvionListAvion != null) {
                    oldVueloOfAvionListAvion.getAvionList().remove(avionListAvion);
                    oldVueloOfAvionListAvion = em.merge(oldVueloOfAvionListAvion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vuelo vuelo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelo persistentVuelo = em.find(Vuelo.class, vuelo.getIdVuelo());
            List<Trayecto> trayectoListOld = persistentVuelo.getTrayectoList();
            List<Trayecto> trayectoListNew = vuelo.getTrayectoList();
            List<Itinireario> itinirearioListOld = persistentVuelo.getItinirearioList();
            List<Itinireario> itinirearioListNew = vuelo.getItinirearioList();
            List<Reserva> reservaListOld = persistentVuelo.getReservaList();
            List<Reserva> reservaListNew = vuelo.getReservaList();
            List<Avion> avionListOld = persistentVuelo.getAvionList();
            List<Avion> avionListNew = vuelo.getAvionList();
            List<String> illegalOrphanMessages = null;
            for (Trayecto trayectoListOldTrayecto : trayectoListOld) {
                if (!trayectoListNew.contains(trayectoListOldTrayecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trayecto " + trayectoListOldTrayecto + " since its vuelo field is not nullable.");
                }
            }
            for (Itinireario itinirearioListOldItinireario : itinirearioListOld) {
                if (!itinirearioListNew.contains(itinirearioListOldItinireario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Itinireario " + itinirearioListOldItinireario + " since its vuelo field is not nullable.");
                }
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its vuelo field is not nullable.");
                }
            }
            for (Avion avionListOldAvion : avionListOld) {
                if (!avionListNew.contains(avionListOldAvion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Avion " + avionListOldAvion + " since its vuelo field is not nullable.");
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
            vuelo.setTrayectoList(trayectoListNew);
            List<Itinireario> attachedItinirearioListNew = new ArrayList<Itinireario>();
            for (Itinireario itinirearioListNewItinirearioToAttach : itinirearioListNew) {
                itinirearioListNewItinirearioToAttach = em.getReference(itinirearioListNewItinirearioToAttach.getClass(), itinirearioListNewItinirearioToAttach.getItinirearioPK());
                attachedItinirearioListNew.add(itinirearioListNewItinirearioToAttach);
            }
            itinirearioListNew = attachedItinirearioListNew;
            vuelo.setItinirearioList(itinirearioListNew);
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getReservaPK());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            vuelo.setReservaList(reservaListNew);
            List<Avion> attachedAvionListNew = new ArrayList<Avion>();
            for (Avion avionListNewAvionToAttach : avionListNew) {
                avionListNewAvionToAttach = em.getReference(avionListNewAvionToAttach.getClass(), avionListNewAvionToAttach.getAvionPK());
                attachedAvionListNew.add(avionListNewAvionToAttach);
            }
            avionListNew = attachedAvionListNew;
            vuelo.setAvionList(avionListNew);
            vuelo = em.merge(vuelo);
            for (Trayecto trayectoListNewTrayecto : trayectoListNew) {
                if (!trayectoListOld.contains(trayectoListNewTrayecto)) {
                    Vuelo oldVueloOfTrayectoListNewTrayecto = trayectoListNewTrayecto.getVuelo();
                    trayectoListNewTrayecto.setVuelo(vuelo);
                    trayectoListNewTrayecto = em.merge(trayectoListNewTrayecto);
                    if (oldVueloOfTrayectoListNewTrayecto != null && !oldVueloOfTrayectoListNewTrayecto.equals(vuelo)) {
                        oldVueloOfTrayectoListNewTrayecto.getTrayectoList().remove(trayectoListNewTrayecto);
                        oldVueloOfTrayectoListNewTrayecto = em.merge(oldVueloOfTrayectoListNewTrayecto);
                    }
                }
            }
            for (Itinireario itinirearioListNewItinireario : itinirearioListNew) {
                if (!itinirearioListOld.contains(itinirearioListNewItinireario)) {
                    Vuelo oldVueloOfItinirearioListNewItinireario = itinirearioListNewItinireario.getVuelo();
                    itinirearioListNewItinireario.setVuelo(vuelo);
                    itinirearioListNewItinireario = em.merge(itinirearioListNewItinireario);
                    if (oldVueloOfItinirearioListNewItinireario != null && !oldVueloOfItinirearioListNewItinireario.equals(vuelo)) {
                        oldVueloOfItinirearioListNewItinireario.getItinirearioList().remove(itinirearioListNewItinireario);
                        oldVueloOfItinirearioListNewItinireario = em.merge(oldVueloOfItinirearioListNewItinireario);
                    }
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Vuelo oldVueloOfReservaListNewReserva = reservaListNewReserva.getVuelo();
                    reservaListNewReserva.setVuelo(vuelo);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldVueloOfReservaListNewReserva != null && !oldVueloOfReservaListNewReserva.equals(vuelo)) {
                        oldVueloOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldVueloOfReservaListNewReserva = em.merge(oldVueloOfReservaListNewReserva);
                    }
                }
            }
            for (Avion avionListNewAvion : avionListNew) {
                if (!avionListOld.contains(avionListNewAvion)) {
                    Vuelo oldVueloOfAvionListNewAvion = avionListNewAvion.getVuelo();
                    avionListNewAvion.setVuelo(vuelo);
                    avionListNewAvion = em.merge(avionListNewAvion);
                    if (oldVueloOfAvionListNewAvion != null && !oldVueloOfAvionListNewAvion.equals(vuelo)) {
                        oldVueloOfAvionListNewAvion.getAvionList().remove(avionListNewAvion);
                        oldVueloOfAvionListNewAvion = em.merge(oldVueloOfAvionListNewAvion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vuelo.getIdVuelo();
                if (findVuelo(id) == null) {
                    throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.");
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
            Vuelo vuelo;
            try {
                vuelo = em.getReference(Vuelo.class, id);
                vuelo.getIdVuelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trayecto> trayectoListOrphanCheck = vuelo.getTrayectoList();
            for (Trayecto trayectoListOrphanCheckTrayecto : trayectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Trayecto " + trayectoListOrphanCheckTrayecto + " in its trayectoList field has a non-nullable vuelo field.");
            }
            List<Itinireario> itinirearioListOrphanCheck = vuelo.getItinirearioList();
            for (Itinireario itinirearioListOrphanCheckItinireario : itinirearioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Itinireario " + itinirearioListOrphanCheckItinireario + " in its itinirearioList field has a non-nullable vuelo field.");
            }
            List<Reserva> reservaListOrphanCheck = vuelo.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable vuelo field.");
            }
            List<Avion> avionListOrphanCheck = vuelo.getAvionList();
            for (Avion avionListOrphanCheckAvion : avionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Avion " + avionListOrphanCheckAvion + " in its avionList field has a non-nullable vuelo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vuelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vuelo> findVueloEntities() {
        return findVueloEntities(true, -1, -1);
    }

    public List<Vuelo> findVueloEntities(int maxResults, int firstResult) {
        return findVueloEntities(false, maxResults, firstResult);
    }

    private List<Vuelo> findVueloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vuelo.class));
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

    public Vuelo findVuelo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vuelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVueloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vuelo> rt = cq.from(Vuelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
