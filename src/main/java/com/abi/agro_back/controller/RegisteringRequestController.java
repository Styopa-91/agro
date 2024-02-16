package com.abi.agro_back.controller;

import com.abi.agro_back.collection.RegisteringRequest;
import com.abi.agro_back.service.RegisteringRequestService;
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
@RequestMapping("/api/reg-request")
@Tag(name = "RegisteringRequest", description = "the Registering Request From User Endpoint")
public class RegisteringRequestController {

    @Autowired
    private RegisteringRequestService registeringRequestService;

    @PostMapping
    public ResponseEntity<RegisteringRequest> createRegisteringRequest(@Validated @RequestBody RegisteringRequest registeringRequest) {
        RegisteringRequest savedRegisteringRequest = registeringRequestService.createRegisteringRequest(registeringRequest);
        return new ResponseEntity<>(savedRegisteringRequest, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RegisteringRequest> getRegisteringRequestById(@PathVariable("id") String id) {
        RegisteringRequest registeringRequest = registeringRequestService.getRegisteringRequestById(id);
        return ResponseEntity.ok(registeringRequest);
    }

    @GetMapping()
    public ResponseEntity<List<RegisteringRequest>> getAllRegisteringRequests() {
        List<RegisteringRequest> registeringRequests = registeringRequestService.getAllRegisteringRequests();
        return ResponseEntity.ok(registeringRequests);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<RegisteringRequest> updateRegisteringRequest(@PathVariable("id") String  registeringRequestId,
                                              @RequestBody RegisteringRequest updatedRegisteringRequest) {
        RegisteringRequest registeringRequest = registeringRequestService.updateRegisteringRequest(registeringRequestId, updatedRegisteringRequest);
        return ResponseEntity.ok(registeringRequest);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRegisteringRequestById(@PathVariable("id") String  id) {
        registeringRequestService.deleteRegisteringRequest(id);
        return ResponseEntity.ok("RegisteringRequest deleted successfully!");
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
