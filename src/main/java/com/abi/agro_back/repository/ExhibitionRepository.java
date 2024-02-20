package com.abi.agro_back.repository;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepository extends MongoRepository<Exhibition, String> {
    List<Exhibition> findExhibitionsByStartDateIsBetweenOrEndDateIsBetweenOrderByStartDate(Date from, Date to, Date from1, Date to1);
    List<Exhibition> findExhibitionsByEndDateBeforeOrderByEndDateDesc(Date data);

}
