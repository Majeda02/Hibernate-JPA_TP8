package com.example.repository;

import com.example.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
    List<Reservation> findAll();
}
