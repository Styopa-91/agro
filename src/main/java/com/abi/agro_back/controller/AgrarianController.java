package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.collection.Note;
import com.abi.agro_back.service.AgrarianService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Agrarian> createAgrarian(@RequestPart("image") MultipartFile image,
                                                   @Valid @RequestPart Agrarian agrarian) throws IOException {
        return new ResponseEntity<>(agrarianService.createAgrarian(image, agrarian), HttpStatus.CREATED);
    }

    @PostMapping("/note")
    public ResponseEntity<Note> sendNote(@Valid @RequestBody Note note) {

        return new ResponseEntity<>(agrarianService.sendNote(note), HttpStatus.CREATED);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<List<Note>> getUserNotesByAgrarianId(@PathVariable("id") String id) {

        return ResponseEntity.ok(agrarianService.getUserNotesByAgrarianId(id));
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

    @GetMapping("/priority")
    public ResponseEntity<List<Agrarian>> getAllAgrariansByPriority() {
        return ResponseEntity.ok(agrarianService.getAllAgrariansByPriority());
    }

    @GetMapping("/oblast")
    public ResponseEntity<List<Agrarian>> getAllAgrariansByOblast(@RequestParam("oblast") String oblast) {

        return ResponseEntity.ok(agrarianService.getAllAgrariansByOblast(oblast));
    }

    @GetMapping("/region")
    public ResponseEntity<List<Agrarian>> getAllAgrariansByRegion(@RequestParam("oblast") String oblast, @RequestParam("region") String region) {

        return ResponseEntity.ok(agrarianService.getAllAgrariansByRegion(oblast, region));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Agrarian> updateAgrarian(@PathVariable("id") String  agrarianId,
                                              @RequestBody @Valid Agrarian updatedAgrarian) {
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
