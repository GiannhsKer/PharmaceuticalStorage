package com.gi.pharmacyWarehouse.service;

import com.gi.pharmacyWarehouse.dto.DrugDTO;
import com.gi.pharmacyWarehouse.dto.MovementDTO;
import com.gi.pharmacyWarehouse.exception.DrugException;
import com.gi.pharmacyWarehouse.exception.DuplicateDrugException;
import com.gi.pharmacyWarehouse.exception.InsufficientStockException;
import com.gi.pharmacyWarehouse.exception.NegativeQuantityException;
import com.gi.pharmacyWarehouse.model.*;
import com.gi.pharmacyWarehouse.repository.DrugCategoryRepository;
import com.gi.pharmacyWarehouse.repository.DrugRepository;
import com.gi.pharmacyWarehouse.repository.MovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyService {

    private final DrugRepository drugRepository;
    private final DrugCategoryRepository drugCategoryRepository;
    private final MovementRepository movementRepository;

    @Transactional
    public Drug addNewDrug(DrugDTO request) {
        String drugName = request.name().toLowerCase();
        String categoryName = request.category().toLowerCase();

        DrugCategory drugCategory = drugCategoryRepository.findByName(categoryName)
                .orElseGet(() -> drugCategoryRepository.save(DrugCategory.builder()
                        .name(categoryName)
                        .build()));

        drugRepository.findByName(drugName)
                .ifPresent(d -> {
                    throw new DuplicateDrugException("Drug with name " + drugName + " already exists");
                });

        Drug drug = drugRepository.save(Drug.builder()
                .name(drugName)
                .code(request.code())
                .price(request.price())
                .stock(request.stock())
                .category(drugCategory)
                .build());

        Movement movement = Movement.builder()
                .drug(drug)
                .quantity(drug.getStock())
                .movementDate(LocalDate.now())
                .movementType(Movement.MovementType.IN)
                .build();
        movementRepository.save(movement);
        log.info("Movement created successfully : {}", movement);
        return drug;
    }

    public List<DrugDTO> getAllDrugs() {
        log.info("Fetching all drugs");
        return drugRepository.findAll()
                .stream()
                .map(m -> new DrugDTO(
                        m.getCode(),
                        m.getName(),
                        m.getPrice(),
                        m.getStock(),
                        m.getCategoryNameSnapshot()
                ))
                .toList();
    }

    public List<MovementDTO> getDrugsByDateRange(LocalDate fromDate, LocalDate toDate, List<String> drugList) {
        log.info("Fetching movements between {} and {} for {}", fromDate, fromDate, drugList);
        return movementRepository.findMovementsByDrugNames(fromDate, toDate, drugList)
                .stream()
                .map(m -> new MovementDTO(
                        m.getDrug().getName(),
                        m.getQuantity(),
                        m.getMovementDate(),
                        m.getMovementType()
                ))
                .toList();
    }

    @Transactional
    public MovementDTO changeDrugStock(String drugName, int quantity, Movement.MovementType movementType) {
        if (quantity < 0) {
            throw new NegativeQuantityException("Quantity should be positive");
        }

        Drug drug = drugRepository.findByName(drugName)
                .orElseThrow(() -> new DrugException("Drug " + drugName + " does not exist"));

        int finalQuantity = 0;

        if (movementType.equals(Movement.MovementType.OUT)) {

            finalQuantity = drug.getStock() - quantity;

            if (finalQuantity < 0) {
                throw new InsufficientStockException("Stock cannot go below 0");
            } else if (finalQuantity < 2) {
                log.warn("{} is about to run out!\nQuantity : {}", drugName, quantity);
            }
            drug.setStock(finalQuantity);
            drugRepository.save(drug);

        } else if (movementType.equals(Movement.MovementType.IN)) {
            finalQuantity = drug.getStock() + quantity;
            drug.setStock(finalQuantity);
        }

        Movement movement = Movement.builder()
                .drug(drug)
                .quantity(finalQuantity)
                .movementDate(LocalDate.now())
                .movementType(movementType)
                .build();
        movementRepository.save(movement);

        log.info("Change drug quantity of {} to {}", drugName, quantity);
        return new MovementDTO(drugName, finalQuantity,
                LocalDate.now(), movementType);
    }

}
