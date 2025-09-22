package com.gi.pharmacyWarehouse.repository;

import com.gi.pharmacyWarehouse.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DrugRepository extends JpaRepository<Drug, UUID> {

    Optional<Drug> findByName(String name);

}
