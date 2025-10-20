package com.example.repository;

import com.example.model.Salle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SalleRepository {
    Salle findById(Long id);
    Salle save(Salle salle);
    void delete(Salle salle);
    List<Salle> findAll();
    long count();

    /**
     * Retourne les salles disponibles pour un intervalle (exclut les salles qui ont une réservation chevauchante).
     */
    List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end);

    /**
     * Recherche multi-critères simple. Les clés supportées : "capaciteMin", "capaciteMax", "batiment",
     * "etage", "equipement" (Long id).
     */
    List<Salle> searchRooms(Map<String, Object> criteres);

    /**
     * Pagination basique: pageNum starting at 1
     */
    List<Salle> findPaginated(int pageNum, int pageSize);
}
