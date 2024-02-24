package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.collection.Photo;
import com.abi.agro_back.config.StorageService;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.ExhibitionRepository;
import com.abi.agro_back.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public Exhibition createExhibition(Exhibition exhibition) {

        return exhibitionRepository.save(exhibition);
    }

    @Override
    public Exhibition getExhibitionById(String exhibitionId) {

        return exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Exhibition is not exists with given id : " + exhibitionId));
    }

    @Override
    public List<Exhibition> getAllExhibitions() {

        return exhibitionRepository.findAll();
    }

    @Override
    public Exhibition updateExhibition(String exhibitionId, Exhibition updatedExhibition) {

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId).orElseThrow(
                () -> new ResourceNotFoundException("Exhibition is not exists with given id: " + exhibitionId)
        );
        updatedExhibition.setId(exhibition.getId());

        return exhibitionRepository.save(updatedExhibition);
    }

    @Override
    public void deleteExhibitionById(String exhibitionId) {

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Exhibition is not exists with given id : " + exhibitionId));
        storageService.deletePhoto(exhibition.getImage().getKey());
        storageService.deletePhoto(exhibition.getLogo().getKey());
        for (Photo photo : exhibition.getGallery_photos()){
            storageService.deletePhoto(photo.getKey());
        }
        exhibitionRepository.deleteById(exhibitionId);

    }

    @Override
    public List<Exhibition> getExhibitionsByDate(Date start, Date end) {
        start.setHours(0);
        end.setHours(24);
        return exhibitionRepository.findExhibitionsByStartDateIsBetweenOrEndDateIsBetweenOrderByStartDate(start, end, start, end);
    }

    @Override
    public List<Exhibition> getExhibitionsArchive() {
        Date now = new Date();
        now.setHours(0);
        return exhibitionRepository.findExhibitionsByEndDateBeforeOrderByEndDateDesc(now);
    }

    @Override
    public Page<Exhibition> findAllByPage(Pageable pageable) {
        return exhibitionRepository.findAll(pageable);
    }

    @Override
    public List<Exhibition> getExhibitionsByKeySearch(String key, String oblast) {
        if (!oblast.isEmpty()) {
            return exhibitionRepository.findExhibitionsByKeyWordsIsContainingIgnoreCaseAndOblastIsIgnoreCase(key, oblast);
        } else {
            return exhibitionRepository.findExhibitionsByKeyWordsIsContainingIgnoreCase(key);
        }
    }
}
