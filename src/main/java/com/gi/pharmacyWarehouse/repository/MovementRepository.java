package com.gi.pharmacyWarehouse.repository;

import com.gi.pharmacyWarehouse.model.Drug;
import com.gi.pharmacyWarehouse.model.WarehouseMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<WarehouseMovement, UUID> {


}
