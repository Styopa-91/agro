package com.abi.agro_back.repository;

import com.abi.agro_back.collection.VillageCouncil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VillageCouncilRepository extends MongoRepository<VillageCouncil, String> {
    List<VillageCouncil> findAllByOblastAndOldRegionOrderByTitleAsc(String oblast, String oldRegion);
    long countAllByOblastEquals(String oblast);
    long countAllByOblastEqualsAndOldRegionEquals(String oblast, String region);

}
