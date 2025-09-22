package com.gi.pharmacyWarehouse.model;

import java.time.LocalDate;
import java.util.UUID;

public record MovementResponse(
        UUID id,
        String name,
        int stock,
        LocalDate movementDate
) {
    public static MovementResponse fromEntity(Movement movement) {
        return new MovementResponse(
                movement.getId(),
                movement.getDrug().getName(),
                movement.getQuantity(),
                movement.getMovementDate()
        );
    }
}