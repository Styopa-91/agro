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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ImagePage> createImagePage(@Validated @RequestBody ImagePage imagePage) {
        ImagePage savedImagePage = imagePageService.createImagePage(imagePage);
        return new ResponseEntity<>(savedImagePage, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ImagePage> getImagePageById(@PathVariable("id") String id) {
        ImagePage imagePage = imagePageService.getImagePageById(id);
        return ResponseEntity.ok(imagePage);
    }

    @GetMapping()
    public ResponseEntity<List<ImagePage>> getAllImagePages() {
        List<ImagePage> imagePages = imagePageService.getAllImagePages();
        return ResponseEntity.ok(imagePages);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ImagePage> updateImagePage(@PathVariable("id") String  imagePageId,
                                              @RequestBody ImagePage updatedImagePage) {
        ImagePage imagePage = imagePageService.updateImagePage(imagePageId, updatedImagePage);
        return ResponseEntity.ok(imagePage);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImagePageById(@PathVariable("id") String  id) {
        imagePageService.deleteImagePage(id);
        return ResponseEntity.ok("ImagePage deleted successfully!");
    }

    @GetMapping("/page")
    public Page<ImagePage> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "2") int sizePerPage,
                                          @RequestParam(defaultValue = "ID") SortField sortField,
                                          @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());
        return imagePageService.findAllByPage(pageable);
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
