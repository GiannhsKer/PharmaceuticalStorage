package com.gi.pharmacyWarehouse.controller;

import com.gi.pharmacyWarehouse.dto.ApiResponse;
import com.gi.pharmacyWarehouse.dto.DrugDTO;
import com.gi.pharmacyWarehouse.dto.MovementDTO;
import com.gi.pharmacyWarehouse.model.*;
import com.gi.pharmacyWarehouse.service.PharmacyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacy")
@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping("/addNewDrug")
    public ResponseEntity<ApiResponse<Drug>> addNewDrug(@Valid @RequestBody DrugDTO request) {
        log.info("POST /api/v1/pharmacy - New drug added : {} !", request.name());
        Drug addedDrug = pharmacyService.addNewDrug(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("New drug added successfully", addedDrug));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DrugDTO>>> getAllDrugs() {
        log.info("GET /api/v1/pharmacy - Fetching all drugs");
        List<DrugDTO> drugsList = pharmacyService.getAllDrugs();
        String dbResponse = drugsList.isEmpty() ? "No drugs found in the warehouse" : "Drugs retrieved successfully";
        return ResponseEntity.ok(ApiResponse.success(dbResponse, drugsList));
    }

    @GetMapping("/movements")
    public ResponseEntity<ApiResponse<List<MovementDTO>>> searchMovements(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam List<String> drugNames) {
        log.info("GET /api/v1/pharmacy/movements - Searching movements for drugs : {}, from : {}, to: {}",
                drugNames, fromDate, toDate);
        List<MovementDTO> movements = pharmacyService.getDrugsByDateRange(fromDate, toDate, drugNames);
        return ResponseEntity.ok(ApiResponse.success("Movements retrieved successfully",
                movements));
    }

    @PatchMapping("/sellDrug")
    public ResponseEntity<ApiResponse<MovementDTO>> sellDrug(
            @RequestParam String drugName,
            @RequestParam int quantity) {
        log.info("PATCH /api/v1/pharmacy/sellDrug - Drug  : {}, quantity : {}", drugName, quantity);
        MovementDTO movement = pharmacyService.changeDrugStock(drugName, quantity, Movement.MovementType.OUT);
        return ResponseEntity.ok(ApiResponse.success(quantity + " " + drugName +
                        "(s) taken out of the storage",movement));
    }

    @PatchMapping("/addDrug")
    public ResponseEntity<ApiResponse<MovementDTO>> addDrug(
            @RequestParam String drugName,
            @RequestParam int quantity) {
        log.info("PATCH /api/v1/pharmacy/addDrug - Drug  : {}, quantity : {}", drugName, quantity);
        MovementDTO movement = pharmacyService.changeDrugStock(drugName, quantity, Movement.MovementType.IN);
        return ResponseEntity.ok(ApiResponse.success(quantity + " " + drugName +
                "(s) were added to the storage",movement));
    }

}
