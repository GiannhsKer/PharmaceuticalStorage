package com.gi.pharmacyWarehouse.repository;

import com.gi.pharmacyWarehouse.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, UUID> {

    @Query(value = """
                SELECT m.*
                FROM pharmacy_movements m
                JOIN pharmacy_drugs d ON m.drug_id = d.id
                WHERE m.movement_date BETWEEN :fromDate AND :toDate
                AND m.movement_type = 'OUT'
                AND d.name IN (:drugNames);
            """,
            nativeQuery = true)
    List<Movement> findMovementsByDrugNames(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("drugNames") List<String> drugNames
    );
}
