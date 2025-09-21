package com.gi.pharmacyWarehouse.repository;

import com.gi.pharmacyWarehouse.model.Drug;
import com.gi.pharmacyWarehouse.model.DrugCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DrugCategoryRepository extends JpaRepository<DrugCategory, UUID> {

    Optional<DrugCategory> findByName(String name);

}
