package com.gi.pharmacyWarehouse.controller;

import com.gi.pharmacyWarehouse.exception.DrugException;
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

    @PostMapping("/addDrug")
    public ResponseEntity<ApiResponse<Drug>> createAppointment(@Valid @RequestBody DrugDTO request) {
        log.info("POST /api/v1/pharmacy - New drug added : {} !", request.name());
        Drug addedDrug = pharmacyService.addNewDrug(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("New drug added successfully", addedDrug));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Drug>>> getAllDrugs() {
        log.info("GET /api/v1/pharmacy - Fetching all drugs");
        List<Drug> drugsList = new ArrayList<>(pharmacyService.getAllDrugs());
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

}
