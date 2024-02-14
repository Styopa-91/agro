package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.ExhibitionRepository;
import com.abi.agro_back.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Override
    public Exhibition createExhibition(Exhibition exhibition) {

        Exhibition savedExhibition =exhibitionRepository.save(exhibition);

        return savedExhibition;
    }

    @Override
    public Exhibition getExhibitionById(String exhibitionId) {

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Exhibition is not exists with given id : " + exhibitionId));

        return exhibition;
    }

    @Override
    public List<Exhibition> getAllExhibitions() {

        List <Exhibition> exhibitions = exhibitionRepository.findAll();
        return exhibitions;
    }

    @Override
    public Exhibition updateExhibition(String exhibitionId, Exhibition updatedExhibition) {

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId).orElseThrow(
                () -> new ResourceNotFoundException("Exhibition is not exists with given id: " + exhibitionId)
        );

        Exhibition updatedUserObj = exhibitionRepository.save(updatedExhibition);

        return updatedUserObj;
    }

    @Override
    public void deleteExhibition(String exhibitionId) {

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Exhibition is not exists with given id : " + exhibitionId));

        exhibitionRepository.deleteById(exhibitionId);
    }
}
