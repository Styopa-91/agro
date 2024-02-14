package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.service.ExhibitionService;
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
@RequestMapping("/api/exhibitions")
@Tag(name = "Exhibition", description = "the Exhibition Endpoint")
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @PostMapping
    public ResponseEntity<Exhibition> createExhibition(@Validated @RequestBody Exhibition exhibition) {
        Exhibition savedExhibition = exhibitionService.createExhibition(exhibition);
        return new ResponseEntity<>(savedExhibition, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Exhibition> getExhibitionById(@PathVariable("id") String id) {
        Exhibition exhibition = exhibitionService.getExhibitionById(id);
        return ResponseEntity.ok(exhibition);
    }

    @GetMapping()
    public ResponseEntity<List<Exhibition>> getAllExhibitions() {
        List<Exhibition> exhibitions = exhibitionService.getAllExhibitions();
        return ResponseEntity.ok(exhibitions);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Exhibition> updateExhibition(@PathVariable("id") String  exhibitionId,
                                              @RequestBody Exhibition updatedExhibition) {
        Exhibition exhibition = exhibitionService.updateExhibition(exhibitionId, updatedExhibition);
        return ResponseEntity.ok(exhibition);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExhibitionById(@PathVariable("id") String  id) {
        exhibitionService.deleteExhibition(id);
        return ResponseEntity.ok("Exhibition deleted successfully!");
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
