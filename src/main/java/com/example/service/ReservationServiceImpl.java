package com.example.service;

import com.example.model.Reservation;
import com.example.repository.ReservationRepository;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final EntityManager em;
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(EntityManager em, ReservationRepository reservationRepository) {
        this.em = em;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        try {
            em.getTransaction().begin();
            Reservation saved = reservationRepository.save(reservation);
            em.getTransaction().commit();
            return saved;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        try {
            em.getTransaction().begin();
            Reservation updated = reservationRepository.save(reservation);
            em.getTransaction().commit();
            return updated;
        } catch (OptimisticLockException ole) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ole;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        try {
            em.getTransaction().begin();
            reservationRepository.delete(reservation);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
