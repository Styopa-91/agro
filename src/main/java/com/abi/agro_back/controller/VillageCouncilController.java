package com.abi.agro_back.controller;

import com.abi.agro_back.collection.VillageCouncil;
import com.abi.agro_back.service.VillageCouncilService;
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
@RequestMapping("/api/villageCouncil")
@Tag(name = "Village Council", description = "the Village Council Endpoint")
public class VillageCouncilController {

    @Autowired
    private VillageCouncilService villageCouncilService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<VillageCouncil> createVillageCouncil(@RequestPart("image") MultipartFile image,
                                                         @Valid @RequestPart VillageCouncil villageCouncil) throws IOException {
        return new ResponseEntity<>(villageCouncilService.createVillageCouncil(image, villageCouncil), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<VillageCouncil> getVillageCouncilById(@PathVariable("id") String id) {
        VillageCouncil villageCouncil = villageCouncilService.getVillageCouncilById(id);
        return ResponseEntity.ok(villageCouncil);
    }

    @GetMapping()
    public ResponseEntity<List<VillageCouncil>> getAllVillageCouncils() {
        List<VillageCouncil> villageCouncils = villageCouncilService.getAllVillageCouncils();
        return ResponseEntity.ok(villageCouncils);
    }


    @GetMapping("/region")
    public ResponseEntity<List<VillageCouncil>> getAllVillageCouncilsByRegion(@RequestParam("oblast") String oblast, @RequestParam("region") String region) {

        return ResponseEntity.ok(villageCouncilService.getAllVillageCouncilsByRegion(oblast, region));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<VillageCouncil> updateVillageCouncil(@PathVariable("id") String  villageCouncilId,
                                              @RequestBody @Valid VillageCouncil updatedVillageCouncil) {
        VillageCouncil villageCouncil = villageCouncilService.updateVillageCouncil(villageCouncilId, updatedVillageCouncil);
        return ResponseEntity.ok(villageCouncil);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVillageCouncilById(@PathVariable("id") String  id) {
        villageCouncilService.deleteVillageCouncil(id);
        return ResponseEntity.ok("VillageCouncil deleted successfully!");
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
