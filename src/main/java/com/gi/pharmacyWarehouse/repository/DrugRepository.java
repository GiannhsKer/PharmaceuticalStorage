package com.gi.pharmacyWarehouse.repository;

import com.gi.pharmacyWarehouse.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DrugRepository extends JpaRepository<Drug, UUID> {

    Optional<Drug> findByName(String name);

//    @Query("SELECT a FROM Appointment a WHERE a.phoneNumber = ?1")
//    Optional<Appointment> findByPhone(String phoneNumber);
//
//    @Query("SELECT a FROM Appointment a WHERE a.dateTime BETWEEN :startOfDay AND :endOfDay")
//    List<Appointment> findByDate(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
//
//    @Query("SELECT a FROM Appointment a WHERE a.dateTime > :startTime AND a.dateTime < :endTime AND (:id IS NULL OR a.id <> :id)")
//    List<Appointment> findByDateRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("id") UUID id);

}
