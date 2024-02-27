package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Page;
import com.abi.agro_back.service.PageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pages")
@Tag(name = "Page", description = "the Page Endpoint")
public class PageController {

    @Autowired
    private PageService pageService;

    @PostMapping
    public ResponseEntity<Page> createPage(@RequestBody @Valid Page page) {
        Page savedPage = pageService.createPage(page);
        return new ResponseEntity<>(savedPage, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Page> getPageById(@PathVariable("id") String id) {
        Page page = pageService.getPageById(id);
        return ResponseEntity.ok(page);
    }

    @GetMapping()
    public ResponseEntity<List<Page>> getAllPages() {
        List<Page> pages = pageService.getAllPages();
        return ResponseEntity.ok(pages);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Page> updatePage(@PathVariable("id") String  pageId,
                                              @RequestBody Page updatedPage) {
        Page page = pageService.updatePage(pageId, updatedPage);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePageById(@PathVariable("id") String  id) {
        pageService.deletePage(id);
        return ResponseEntity.ok("Page deleted successfully!");
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
