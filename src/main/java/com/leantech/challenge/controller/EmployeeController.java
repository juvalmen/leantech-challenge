package com.leantech.challenge.controller;

import com.leantech.challenge.pojo.api.EmployeeTO;
import com.leantech.challenge.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/all")
    @Operation(description = "Get Employees")
    @ApiResponse(responseCode = "200", description = "All employees", content = @Content)
    public ResponseEntity<?> getEmployees(@RequestParam(required = false) final String position, @RequestParam(required = false) final String employeeName) {
        log.debug("Get all employees");
        final List<EmployeeTO> allEmployees = employeeService.findAll(position, employeeName);
        return new ResponseEntity<List<EmployeeTO>>(allEmployees, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @Operation(description = "Save Employees")
    @ApiResponse(responseCode = "201", description = "Employee created", content = @Content)
    @ApiResponse(responseCode = "400", description = "Request has illegal arguments. Bad Request", content = @Content)
    public ResponseEntity<?> createEmployee(@Valid @RequestBody final EmployeeTO employeeTO) {
        log.debug("Request to create employee {}", employeeTO);
        final EmployeeTO employeeCreated = employeeService.save(employeeTO);
        return new ResponseEntity<EmployeeTO>(employeeCreated, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    @Operation(description = "Update Employees")
    @ApiResponse(responseCode = "201", description = "Employee updated", content = @Content)
    @ApiResponse(responseCode = "400", description = "Request has illegal arguments. Bad Request", content = @Content)
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody final EmployeeTO employeeTO) {
        log.debug("Request to update employee {}", employeeTO);
        final EmployeeTO employeeUpdate = employeeService.update(employeeTO);
        return new ResponseEntity<EmployeeTO>(employeeUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(description = "Delete Employees")
    @ApiResponse(responseCode = "202", description = "Employee deleted", content = @Content)
    @ApiResponse(responseCode = "400", description = "Request has illegal arguments. Bad Request", content = @Content)
    public ResponseEntity<?> deletemployee(@NotNull @PathVariable final Long id) {
        log.debug("Id to delete employee {}", id);
        employeeService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
