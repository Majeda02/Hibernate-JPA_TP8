package com.example.service;

import com.example.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation findById(Long id);
    Reservation createReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Reservation reservation);
    List<Reservation> findAll();
}
