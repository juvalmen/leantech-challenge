package com.leantech.challenge.controller;

import com.leantech.challenge.pojo.api.PersonTO;
import com.leantech.challenge.service.person.PersonService;
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
@RequestMapping("/api/v1/persons")
@Tag(name = "Persons")
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/all")
    @Operation(description = "Get Persons")
    @ApiResponse(responseCode = "200", description = "All persons", content = @Content)
    public ResponseEntity<?> getPersons() {
        log.debug("Get all persons");
        final List<PersonTO> allPersons = personService.findAll();
        return new ResponseEntity<List<PersonTO>>(allPersons, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @Operation(description = "Save Persons")
    @ApiResponse(responseCode = "201", description = "Person created", content = @Content)
    @ApiResponse(responseCode = "400", description = "Request has illegal arguments. Bad Request", content = @Content)
    public ResponseEntity<?> createPerson(@Valid @RequestBody final PersonTO personTO) {
        log.debug("Request to create person {}", personTO);
        final PersonTO personCreated = personService.save(personTO);
        return new ResponseEntity<PersonTO>(personCreated, HttpStatus.CREATED);
    }
}
