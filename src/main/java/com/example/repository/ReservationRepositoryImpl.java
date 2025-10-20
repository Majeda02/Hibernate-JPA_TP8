package com.example.repository;

import com.example.model.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final EntityManager em;

    public ReservationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getId() == null) {
            em.persist(reservation);
            return reservation;
        } else {
            return em.merge(reservation);
        }
    }

    @Override
    public void delete(Reservation reservation) {
        Reservation managed = reservation;
        if (!em.contains(reservation)) {
            managed = em.merge(reservation);
        }
        em.remove(managed);
    }

    @Override
    public List<Reservation> findAll() {
        TypedQuery<Reservation> q = em.createQuery("SELECT r FROM Reservation r", Reservation.class);
        return q.getResultList();
    }
}
