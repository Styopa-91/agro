package com.abi.agro_back.repository;

import com.abi.agro_back.collection.Agrarian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AgrarianRepository extends MongoRepository<Agrarian, String> {
    List<Agrarian> findAllByOrderByIsPriorityDescTitleAsc();
    List<Agrarian> findAllByOblastAndIsForOblastOnlyTrueOrderByIsPriorityDescTitleAsc(String oblast);
    Page<Agrarian> findAllByOblastAndOldRegionOrderByIsPriorityDescTitleAsc(String oblast, String region, Pageable pageable);
    Page<Agrarian> findAllByOblastAndOldRegionOrderByImageDescTitleAsc(String oblast, String region, Pageable pageable);
    long countAllByOblastEquals(String oblast);
    long countAllByOblastEqualsAndOldRegionEquals(String oblast, String region);
}
