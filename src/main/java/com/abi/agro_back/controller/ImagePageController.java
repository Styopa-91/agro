package com.abi.agro_back.controller;

import com.abi.agro_back.collection.ImagePage;
import com.abi.agro_back.collection.SortField;
import com.abi.agro_back.service.ImagePageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/imagePages")
@Tag(name = "ImagePage", description = "the ImagePage Endpoint")
public class ImagePageController {

    @Autowired
    private ImagePageService imagePageService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ImagePage> createImagePage(@RequestPart("image") MultipartFile image,
                                                     @RequestPart ImagePage imagePage) throws IOException {

        return new ResponseEntity<>(imagePageService.createImagePage(image, imagePage), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ImagePage> getImagePageById(@PathVariable("id") String id) {

        return ResponseEntity.ok(imagePageService.getImagePageById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ImagePage>> getAllImagePages() {

        return ResponseEntity.ok(imagePageService.getAllImagePages());
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ImagePage> updateImagePage(@PathVariable("id") String  imagePageId,
                                              @RequestBody ImagePage updatedImagePage) {
        return ResponseEntity.ok(imagePageService.updateImagePage(imagePageId, updatedImagePage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImagePageById(@PathVariable("id") String  id) {
        imagePageService.deleteImagePage(id);
        return ResponseEntity.ok("ImagePage deleted successfully!");
    }

    @GetMapping("/page")
    public Page<ImagePage> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int sizePerPage,
                                          @RequestParam(defaultValue = "TITLE") SortField sortField,
                                          @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());
        return imagePageService.findAllByPage(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ImagePage>> getImagePagesByKeySearch(@RequestParam String  key, @RequestParam(defaultValue = "") String oblast) {
        return ResponseEntity.ok(imagePageService.getImagePagesByKeySearch(key, oblast));
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
