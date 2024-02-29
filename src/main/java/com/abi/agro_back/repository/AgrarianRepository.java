package com.abi.agro_back.repository;

import com.abi.agro_back.collection.Agrarian;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AgrarianRepository extends MongoRepository<Agrarian, String> {
    List<Agrarian> findAllByOrderByIsPriorityDescTitleAsc();
    List<Agrarian> findAllByOblastOrderByIsPriorityDescTitleAsc(String oblast);
    List<Agrarian> findAllByOblastAndOldRegionOrderByIsPriorityDescTitleAsc(String oblast);

}
