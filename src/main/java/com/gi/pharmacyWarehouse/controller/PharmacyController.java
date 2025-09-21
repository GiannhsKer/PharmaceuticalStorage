package com.gi.pharmacyWarehouse.controller;

import com.gi.pharmacyWarehouse.exception.DrugException;
import com.gi.pharmacyWarehouse.model.ApiResponse;
import com.gi.pharmacyWarehouse.model.Drug;
import com.gi.pharmacyWarehouse.model.DrugRequest;
import com.gi.pharmacyWarehouse.service.PharmacyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pharmacy")
@Slf4j
@CrossOrigin(origins = "*")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/addDrug")
    public ResponseEntity<ApiResponse<Drug>> createAppointment(@Valid @RequestBody DrugRequest request) {
        log.info("POST /api/v1/pharmacy - New drug added : {} !", request.getName());
        try {
            Drug addedDrug = pharmacyService.addNewDrug(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.success("New drug added successfully", addedDrug));
        } catch (DrugException e) {
            log.error("Error adding drug: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }


//        @GetMapping
//        public ResponseEntity<ApiResponse<List<Drug>>> getAllAppointments(@PathVariable String token) {
//            log.info("GET /api/v1/appointments - Fetching all appointments");
//            List<Appointment> appointments = new ArrayList<>(appointmentService.getAllAppointments());
//            authenticateRequest(token);
//
//            return ResponseEntity.ok(ApiResponse.success("Appointments retrieved successfully", appointments));
//        }

//        @GetMapping("/{id}")
//        public ResponseEntity<ApiResponse<Appointment>> getAppointmentById(@PathVariable UUID id) {
//            log.info("GET /api/v1/appointments/{} - Fetching appointment by id", id);
//            return appointmentService.getAppointmentById(id)
//                    .map(appointment -> ResponseEntity.ok(ApiResponse.success("Appointment retrieved successfully", appointment)))
//                    .orElseGet(() ->ResponseEntity.status(HttpStatus.NOT_FOUND)
//                            .body(ApiResponse.error("Appointment not found with id: " + id)));
//        }

//        @GetMapping("/search")
//        public ResponseEntity<ApiResponse<List<Appointment>>> searchAppointments(
//                @RequestParam(required = false) String name,
//                @RequestParam(required = false) String phone,
//                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//
//            log.info("GET /api/v1/appointments/search - Searching appointments with name: {}, phone: {}, date: {}", name, phone, date);
//
//            List<Appointment> appointments;
//
//            if (name != null) {
//                appointments = new ArrayList<>(appointmentService.getAppointmentByName(name).stream().toList());
//            } else if (phone != null) {
//                appointments = new ArrayList<>(appointmentService.getAppointmentByPhone(phone).stream().toList());
//            } else if (date != null) {
//                appointments = new ArrayList<>(appointmentService.getAppointmentsByDate(date));
//            } else if (startDate != null && endDate != null) {
//                appointments = new ArrayList<>(appointmentService.getAppointmentsByDateRange(startDate, endDate));
//            } else {
//                appointments = new ArrayList<>(appointmentService.getAllAppointments());
//            }
//
//            return ResponseEntity.ok(ApiResponse.success("Appointments retrieved successfully", appointments));
//        }

//        @PatchMapping("/{id}")
//        public ResponseEntity<ApiResponse<Appointment>> updateAppointment(
//                @Valid @RequestBody AppointmentRequest request, @PathVariable UUID id) {
//            log.info("PATCH /api/v1/appointments/{} - Updating appointment", id);
//            try {
//                Appointment updatedAppointment = appointmentService.updateAppointment(request, id);
//                return ResponseEntity.ok(ApiResponse.success("Appointment updated successfully", updatedAppointment));
//            } catch (AppointmentException e) {
//                log.error("Error updating appointment: {}", e.getMessage());
//                return ResponseEntity.badRequest()
//                        .body(ApiResponse.error(e.getMessage()));
//            }
//        }
//
//        @DeleteMapping("/{id}")
//        public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable UUID id) {
//            log.info("DELETE /api/v1/appointments/{} - Deleting appointment", id);
//            try {
//                appointmentService.deleteAppointment(id);
//                return ResponseEntity.ok(ApiResponse.success("Appointment deleted successfully", null));
//            } catch (AppointmentException e) {
//                log.error("Error deleting appointment: {}", e.getMessage());
//                return ResponseEntity.badRequest()
//                        .body(ApiResponse.error(e.getMessage()));
//            }
//        }
}
