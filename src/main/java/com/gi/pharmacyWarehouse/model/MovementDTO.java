package com.gi.pharmacyWarehouse.model;

import java.time.LocalDate;
import java.util.UUID;

public record MovementDTO(
        UUID id,
        String name,
        int stock,
        LocalDate movementDate
) {
    public static MovementDTO fromEntity(Movement movement) {
        return new MovementDTO(
                movement.getId(),
                movement.getDrug().getName(),
                movement.getQuantity(),
                movement.getMovementDate()
        );
    }
}