package com.abi.agro_back.repository;

import com.abi.agro_back.collection.ImagePage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagePageRepository extends MongoRepository<ImagePage, String> {
    List<ImagePage> findImagePagesByKeyWordsIsContainingIgnoreCase(String key);
}
