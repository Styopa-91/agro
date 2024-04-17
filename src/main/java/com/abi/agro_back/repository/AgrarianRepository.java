package com.abi.agro_back.repository;

import com.abi.agro_back.collection.Agrarian;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgrarianRepository extends MongoRepository<Agrarian, String> {
    List<Agrarian> findAllByOrderByIsPriorityDescTitleAsc();
    List<Agrarian> findAllByOblastAndIsForOblastOnlyTrueOrderByIsPriorityDescTitleAsc(String oblast);
    @Aggregation(pipeline = {
            "{'$match': {'oblast': ?0, 'oldRegion': ?1}}",
            "{ $addFields: { 'fillScore': { $sum: [ { $cond: [{ $gt: [{ $size: '$phones' }, 0] }, 1, 0] }, { $cond: [{ $gt: [{ $size: '$emails' }, 0] }, 1, 0] }, {$cond: [{ $eq: [{ $type: '$image' }, 'object'] }, 5, 0]} ] } } }",
            "{ $sort: { 'fillScore': -1, 'title': 1 } }",
            "{'$skip': ?2}",
            "{'$limit': ?3}"
    })
    List<Agrarian> findAllByOblastAndOldRegion(String oblast, String region, int skip, int limit);
    List<Agrarian> findAllByOblastAndOldRegionOrderByImageDescTitleAsc(String oblast, String region);
    long countAllByOblastEquals(String oblast);
    long countAllByOblastEqualsAndOldRegionEquals(String oblast, String region);
    @Aggregation(pipeline = {
            "{'$match': {'oblast': ?0, 'oldRegion': ?1}}",
            "{ $addFields: { 'fillScore': { $sum: [ { $cond: [{ $gt: [{ $size: '$phones' }, 0] }, 1, 0] }, { $cond: [{ $gt: [{ $size: '$emails' }, 0] }, 1, 0] }] } } }",
            "{ $sort: { 'fillScore': -1, 'title': 1 } }"
    })
    List<Agrarian> findAllByOblastAndOldRegion(String oblast, String region);
}
