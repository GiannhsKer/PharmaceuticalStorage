package com.gi.pharmacyWarehouse.dto;

import com.gi.pharmacyWarehouse.model.Movement;

import java.time.LocalDate;
import java.util.UUID;

public record MovementDTO(
        String drugName,
        int stock,
        LocalDate movementDate,
        Movement.MovementType movementType
) {
    public static MovementDTO fromEntity(Movement movement) {
        return new MovementDTO(
                movement.getDrug().getName(),
                movement.getQuantity(),
                movement.getMovementDate(),
                movement.getMovementType()
        );
    }
}