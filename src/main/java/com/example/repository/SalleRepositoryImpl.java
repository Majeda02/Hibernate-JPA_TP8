package com.example.repository;

import com.example.model.Salle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalleRepositoryImpl implements SalleRepository {

    private final EntityManager em;

    public SalleRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Salle findById(Long id) {
        return em.find(Salle.class, id);
    }

    @Override
    public Salle save(Salle salle) {
        if (salle.getId() == null) {
            em.persist(salle);
            return salle;
        } else {
            return em.merge(salle);
        }
    }

    @Override
    public void delete(Salle salle) {
        Salle managed = salle;
        if (!em.contains(salle)) {
            managed = em.merge(salle);
        }
        em.remove(managed);
    }

    @Override
    public List<Salle> findAll() {
        return em.createQuery("SELECT s FROM Salle s", Salle.class).getResultList();
    }

    @Override
    public long count() {
        Long c = em.createQuery("SELECT COUNT(s) FROM Salle s", Long.class).getSingleResult();
        return c == null ? 0 : c;
    }

    @Override
    public List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT DISTINCT s FROM Salle s " +
                "WHERE s.id NOT IN (" +
                "  SELECT r.salle.id FROM Reservation r " +
                "  WHERE (r.dateDebut <= :end AND r.dateFin >= :start) AND r.statut = :statut" +
                ")";
        TypedQuery<Salle> query = em.createQuery(jpql, Salle.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("statut", com.example.model.StatutReservation.CONFIRMEE);
        return query.getResultList();
    }

    @Override
    public List<Salle> searchRooms(Map<String, Object> criteres) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT s FROM Salle s LEFT JOIN s.equipements e WHERE 1=1 ");
        if (criteres == null) criteres = new HashMap<String, Object>();

        if (criteres.containsKey("capaciteMin")) {
            sb.append(" AND s.capacite >= :capaciteMin");
        }
        if (criteres.containsKey("capaciteMax")) {
            sb.append(" AND s.capacite <= :capaciteMax");
        }
        if (criteres.containsKey("batiment")) {
            sb.append(" AND s.batiment = :batiment");
        }
        if (criteres.containsKey("etage")) {
            sb.append(" AND s.etage = :etage");
        }
        if (criteres.containsKey("equipement")) {
            sb.append(" AND e.id = :equipementId");
        }

        TypedQuery<Salle> q = em.createQuery(sb.toString(), Salle.class);

        if (criteres.containsKey("capaciteMin")) {
            q.setParameter("capaciteMin", criteres.get("capaciteMin"));
        }
        if (criteres.containsKey("capaciteMax")) {
            q.setParameter("capaciteMax", criteres.get("capaciteMax"));
        }
        if (criteres.containsKey("batiment")) {
            q.setParameter("batiment", criteres.get("batiment"));
        }
        if (criteres.containsKey("etage")) {
            q.setParameter("etage", criteres.get("etage"));
        }
        if (criteres.containsKey("equipement")) {
            q.setParameter("equipementId", criteres.get("equipement"));
        }

        return q.getResultList();
    }

    @Override
    public List<Salle> findPaginated(int pageNum, int pageSize) {
        if (pageNum < 1) pageNum = 1;
        int firstResult = (pageNum - 1) * pageSize;
        return em.createQuery("SELECT s FROM Salle s ORDER BY s.id", Salle.class)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
