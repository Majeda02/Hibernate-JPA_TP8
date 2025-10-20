package com.example.service;

import com.example.model.Salle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SalleService {
    Salle findById(Long id);
    Salle createOrUpdateSalle(Salle salle);
    void deleteSalle(Salle salle);
    List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end);
    List<Salle> searchRooms(Map<String, Object> criteres);
    long countRooms();
    int getTotalPages(int pageSize);
    List<Salle> getPaginatedRooms(int pageNum, int pageSize);
}
