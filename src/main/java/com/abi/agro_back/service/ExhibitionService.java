package com.abi.agro_back.service;

import com.abi.agro_back.collection.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ExhibitionService {
    Exhibition createExhibition(Exhibition exhibition);

    Exhibition getExhibitionById(String exhibitionId);

    List<Exhibition> getAllExhibitions();

    Exhibition updateExhibition(String exhibitionId, Exhibition updatedExhibition);

    void deleteExhibitionById(String  exhibitionId);

    List<Exhibition> getExhibitionsByDate(Date start, Date end);

    List<Exhibition> getExhibitionsArchive();

    Page<Exhibition> findAllByPage(Pageable pageable);

    List<Exhibition> getExhibitionsByKeySearch(String key, String oblast);
}
