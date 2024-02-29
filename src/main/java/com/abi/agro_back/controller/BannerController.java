package com.abi.agro_back.controller;

import com.abi.agro_back.collection.Banner;
import com.abi.agro_back.collection.SortField;
import com.abi.agro_back.service.BannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/banners")
@Tag(name = "Banner", description = "the Banner Endpoint")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping
    public ResponseEntity<Banner> createBanner(@Valid @RequestBody Banner banner) {

        return new ResponseEntity<>(bannerService.createBanner(banner), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable("id") String id) {

        return ResponseEntity.ok(bannerService.getBannerById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Banner>> getAllBanners() {

        return ResponseEntity.ok(bannerService.getAllBanners());
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable("id") String  bannerId,
                                              @RequestBody @Valid Banner updatedBanner) {

        return ResponseEntity.ok(bannerService.updateBanner(bannerId, updatedBanner));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBannerById(@PathVariable("id") String  id) {

        bannerService.deleteBanner(id);

        return ResponseEntity.ok("Banner deleted successfully!");
    }

    @GetMapping("/page")
    public Page<Banner> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int sizePerPage,
                                          @RequestParam(defaultValue = "CREATED_AT") SortField sortField,
                                          @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());

        return bannerService.findAllByPage(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Banner>> getBannersByKeySearch(@RequestParam String  key, @RequestParam(defaultValue = "") String oblast) {

        return ResponseEntity.ok(bannerService.getBannersByKeySearch(key, oblast));
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
