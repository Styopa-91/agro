package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.collection.Photo;
import com.abi.agro_back.collection.SortField;
import com.abi.agro_back.config.StorageService;
import com.abi.agro_back.service.ExhibitionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/exhibitions")
@Tag(name = "Exhibition", description = "the Exhibition Endpoint")
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private StorageService storageService;
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Exhibition> createExhibition(@RequestPart("photos") List<MultipartFile> photos, @RequestPart("image") MultipartFile image, @RequestPart("logo") MultipartFile logo, @Validated @RequestPart("exhibition") Exhibition exhibition) throws IOException {
        exhibition.setGallery_photos(new ArrayList<>());
        for (MultipartFile f : photos) {
            String key = f.getOriginalFilename() + "" + System.currentTimeMillis();
            URL url = storageService.uploadPhoto(f, key);
            Photo photo = new Photo(key, url);
            exhibition.getGallery_photos().add(photo);
        }

        String imageKey = image.getOriginalFilename() + "" + System.currentTimeMillis();
        URL imageUrl = storageService.uploadPhoto(image, imageKey);
        Photo imagePhoto = new Photo(imageKey, imageUrl);
        exhibition.setImage(imagePhoto);

        String logoKey = logo.getOriginalFilename() + "" + System.currentTimeMillis();
        URL logoUrl = storageService.uploadPhoto(logo, logoKey);
        Photo logoPhoto = new Photo(logoKey, logoUrl);
        exhibition.setLogo(logoPhoto);

        Exhibition savedExhibition = exhibitionService.createExhibition(exhibition);
        return new ResponseEntity<>(savedExhibition, HttpStatus.CREATED);
    }

    @PostMapping(path = "/photo/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<Exhibition> addImagesById(@RequestPart("photos") List<MultipartFile> photos, @PathVariable("id") String id) throws IOException {
        Exhibition exhibition = exhibitionService.getExhibitionById(id);
        for (MultipartFile f : photos) {
            String key = f.getOriginalFilename() + "" + System.currentTimeMillis();
            URL url = storageService.uploadPhoto(f, key);
            Photo photo = new Photo(key, url);
            exhibition.getGallery_photos().add(photo);
        }

        Exhibition savedExhibition = exhibitionService.createExhibition(exhibition);
        return new ResponseEntity<>(savedExhibition, HttpStatus.CREATED);
    }

    @DeleteMapping("/photo")
    public ResponseEntity<String> deletePhotoByKey(@RequestParam("key") String  key, @RequestParam(value = "id") String  id) {
        storageService.deletePhoto(key);
        Exhibition exhibition = exhibitionService.getExhibitionById(id);
        exhibition.getGallery_photos().removeIf(p -> p.getKey().equals(key));

        exhibitionService.updateExhibition(exhibition.getId(), exhibition);
        return ResponseEntity.ok("Photo deleted successfully!");
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
        exhibitionService.deleteExhibitionById(id);
        return ResponseEntity.ok("Exhibition deleted successfully!");
    }

    @GetMapping("/page")
    public Page<Exhibition> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int sizePerPage,
                                          @RequestParam(defaultValue = "START_DATE") SortField sortField,
                                          @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());
        return exhibitionService.findAllByPage(pageable);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Exhibition>> getExhibitionsByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date end) {
        List<Exhibition> exhibitions = exhibitionService.getExhibitionsByDate(start, end);
        return ResponseEntity.ok(exhibitions);
    }
    @GetMapping("/archive")
        public ResponseEntity<List<Exhibition>> getExhibitionsArchive() {
            List<Exhibition> exhibitions = exhibitionService.getExhibitionsArchive();
            return ResponseEntity.ok(exhibitions);
        }

    @GetMapping("/search")
    public ResponseEntity<List<Exhibition>> getExhibitionsByKeySearch(@RequestParam String  key, @RequestParam(defaultValue = "") String oblast) {
        List<Exhibition> exhibitions = exhibitionService.getExhibitionsByKeySearch(key, oblast);
        return ResponseEntity.ok(exhibitions);
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
