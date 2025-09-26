package com.gi.pharmacyWarehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "pharmacy_drugs")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // required by JPA, but protected so not misused
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // safer equality (by id only)
public class Drug {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Exclude
    private UUID id;

    @Column(name = "code", nullable = false, unique = true)
    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price", nullable = false)
    private double price;

    @Min(value = 0, message = "Stock must be 0 or greater")
    @Column(name = "stock", nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name",referencedColumnName = "name")  // foreign key column
    private DrugCategory category;

}
