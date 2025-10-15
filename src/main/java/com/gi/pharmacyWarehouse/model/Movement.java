package com.gi.pharmacyWarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pharmacy_movements")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // required by JPA
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movement {

    @Id
    @GeneratedValue
    @UuidGenerator
    @EqualsAndHashCode.Include
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @NotNull(message = "Drug is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;

    @Column(name = "drug_name_snapshot", nullable = false)
    private String drugNameSnapshot;

    @PrePersist
    public void prePersist() {
        if (this.drug != null) {
            this.drugNameSnapshot = this.drug.getName();
        }
    }

    @Min(value = 0, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @NotNull(message = "Movement date is required")
    @Column(name = "date", nullable = false)
    private LocalDate movementDate;

    @NotNull(message = "Movement type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MovementType movementType;

    // Enum for movement type
    public enum MovementType {
        IN,
        OUT
    }
}
