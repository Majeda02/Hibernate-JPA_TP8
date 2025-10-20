package com.example.service;

import com.example.model.Salle;
import com.example.repository.SalleRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SalleServiceImpl implements SalleService {

    private final EntityManager em;
    private final SalleRepository salleRepository;

    public SalleServiceImpl(EntityManager em, SalleRepository salleRepository) {
        this.em = em;
        this.salleRepository = salleRepository;
    }

    @Override
    public Salle findById(Long id) {
        return salleRepository.findById(id);
    }

    @Override
    public Salle createOrUpdateSalle(Salle salle) {
        try {
            em.getTransaction().begin();
            Salle saved = salleRepository.save(salle);
            em.getTransaction().commit();
            return saved;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void deleteSalle(Salle salle) {
        try {
            em.getTransaction().begin();
            salleRepository.delete(salle);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end) {
        return salleRepository.findAvailableRooms(start, end);
    }

    @Override
    public List<Salle> searchRooms(Map<String, Object> criteres) {
        return salleRepository.searchRooms(criteres);
    }

    @Override
    public long countRooms() {
        return salleRepository.count();
    }

    @Override
    public int getTotalPages(int pageSize) {
        if (pageSize <= 0) return 0;
        long total = countRooms();
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public List<Salle> getPaginatedRooms(int pageNum, int pageSize) {
        return salleRepository.findPaginated(pageNum, pageSize);
    }
}
