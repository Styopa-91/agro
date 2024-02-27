package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.collection.Note;
import com.abi.agro_back.collection.Photo;
import com.abi.agro_back.collection.User;
import com.abi.agro_back.config.StorageService;
import com.abi.agro_back.repository.AgrarianRepository;
import com.abi.agro_back.repository.NoteRepository;
import com.abi.agro_back.repository.UserRepository;
import com.abi.agro_back.service.AgrarianService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
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
    @Autowired
    private StorageService storageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AgrarianRepository agrarianRepository;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Agrarian> createAgrarian(@RequestPart("image") MultipartFile image,
                                                   @Valid @RequestPart Agrarian agrarian) throws IOException {
        String imageKey = image.getOriginalFilename() + "" + System.currentTimeMillis();
        URL imageUrl = storageService.uploadPhoto(image, imageKey);
        Photo imagePhoto = new Photo(imageKey, imageUrl);
        agrarian.setImage(imagePhoto);
        Agrarian savedAgrarian = agrarianService.createAgrarian(agrarian);
        return new ResponseEntity<>(savedAgrarian, HttpStatus.CREATED);
    }

    @PostMapping("/note")
    public ResponseEntity<Note> sendNote(@Valid @RequestBody Note note) {
        String currentUserName = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userRepository.findByEmail(currentUserName).get();
        note.setUserId(user.getId());
        note = noteRepository.save(note);

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<List<Note>> getUserNotesByAgrarianId(@PathVariable("id") String id) {
        String currentUserName = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userRepository.findByEmail(currentUserName).get();
        List<Note> list = noteRepository.findAllByAgrarianIdIsAndUserIdIsOrderByCreatedAt(id, user.getId());
        return ResponseEntity.ok(list);
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
        List<Agrarian> agrarians = agrarianRepository.findAllByOrderByIsPriorityDescTitleAsc();
        return ResponseEntity.ok(agrarians);
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
