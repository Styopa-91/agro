package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.service.AgrarianService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/agrarians")
@Tag(name = "Agrarian", description = "the Agrarian Endpoint")
public class AgrarianController {

    @Autowired
    private AgrarianService agrarianService;

    @PostMapping
    public ResponseEntity<Agrarian> createAgrarian(@Validated @RequestBody Agrarian agrarian) {
        Agrarian savedAgrarian = agrarianService.createAgrarian(agrarian);
        return new ResponseEntity<>(savedAgrarian, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Agrarian> getAgrarianById(@PathVariable("id") String id) {
        Agrarian agrarian = agrarianService.getAgrarianById(id);
        return ResponseEntity.ok(agrarian);
    }

    @GetMapping()
    public ResponseEntity<List<Agrarian>> getAllAgrarians() {
        List<Agrarian> agrarians = agrarianService.getAllAgrarians();
        return ResponseEntity.ok(agrarians);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Agrarian> updateAgrarian(@PathVariable("id") String  agrarianId,
                                              @RequestBody Agrarian updatedAgrarian) {
        Agrarian agrarian = agrarianService.updateAgrarian(agrarianId, updatedAgrarian);
        return ResponseEntity.ok(agrarian);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAgrarianById(@PathVariable("id") String  id) {
        agrarianService.deleteAgrarian(id);
        return ResponseEntity.ok("Agrarian deleted successfully!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
