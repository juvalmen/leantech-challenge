package com.leantech.challenge.controller;

import com.leantech.challenge.pojo.api.PositionCustomSearchTO;
import com.leantech.challenge.pojo.api.PositionTO;
import com.leantech.challenge.service.position.PositionService;
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
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/positions")
@Tag(name = "Positions")
@Slf4j
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping(value = "/all")
    @Operation(description = "Get Positions")
    @ApiResponse(responseCode = "200", description = "All positions", content = @Content)
    public ResponseEntity<?> getPositions() {
        log.debug("Get all positions");
        final List<PositionTO> allPositions = positionService.findAll();
        return new ResponseEntity<List<PositionTO>>(allPositions, HttpStatus.OK);
    }

    @GetMapping(value = "/all-sorted-by-salary")
    @Operation(description = "Get Positions sorted by salary")
    @ApiResponse(responseCode = "200", description = "All positions sorted by salary", content = @Content)
    public ResponseEntity<?> getPositionsSortedBySalary() {
        log.debug("Get all positions sorted by salary");
        final PositionCustomSearchTO allPositionsSorted = positionService.findPositionsSortedByEmployeeSalary();
        return new ResponseEntity<PositionCustomSearchTO>(allPositionsSorted, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @Operation(description = "Save Positions")
    @ApiResponse(responseCode = "201", description = "Position created", content = @Content)
    @ApiResponse(responseCode = "400", description = "Request has illegal arguments. Bad Request", content = @Content)
    public ResponseEntity<?> createPosition(@Valid @RequestBody final PositionTO positionTO) {
        log.debug("Request to create position {}", positionTO);
        final PositionTO positionCreated = positionService.save(positionTO);
        return new ResponseEntity<PositionTO>(positionCreated, HttpStatus.CREATED);
    }
}
