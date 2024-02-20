package com.abi.agro_back.service;

import com.abi.agro_back.collection.Exhibition;

import java.util.Date;
import java.util.List;

public interface ExhibitionService {
    Exhibition createExhibition(Exhibition exhibition);

    Exhibition getExhibitionById(String exhibitionId);

    List<Exhibition> getAllExhibitions();

    Exhibition updateExhibition(String exhibitionId, Exhibition updatedExhibition);

    void deleteExhibition(String  exhibitionId);

    List<Exhibition> getExhibitionsByDate(Date start, Date end);

    List<Exhibition> getExhibitionsArchive();
}
