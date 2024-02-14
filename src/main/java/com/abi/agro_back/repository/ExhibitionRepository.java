package com.abi.agro_back.repository;

import com.abi.agro_back.collection.Exhibition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionRepository extends MongoRepository<Exhibition, String> {

}
