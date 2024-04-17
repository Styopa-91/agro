package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.collection.VillageCouncil;
import com.abi.agro_back.repository.AgrarianRepository;
import com.abi.agro_back.repository.DemoRepository;
import com.abi.agro_back.repository.VillageCouncilRepository;
import com.abi.agro_back.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private AgrarianRepository agrarianRepository;
    @Autowired
    private DemoRepository demoRepository;
    @Autowired
    private VillageCouncilRepository villageCouncilRepository;

    @Override
    public Page<Agrarian> getAllAgrariansByRegion(Pageable pageable) {
        String oblast = demoRepository.findAll().get(0).getOblast();
        String region = demoRepository.findAll().get(0).getOldRegion();
        int skip = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<Agrarian> documents = agrarianRepository.findAllByOblastAndOldRegion(oblast, region, skip, limit);
        Long totalCount = agrarianRepository.countAllByOblastEqualsAndOldRegionEquals(oblast, region);

        return new PageImpl<>(documents, pageable, totalCount);
    }

    @Override
    public List<Agrarian> getAllDemoAgrarians() {
        String oblast = demoRepository.findAll().get(0).getOblast();
        String region = demoRepository.findAll().get(0).getOldRegion();
        return agrarianRepository.findAllByOblastAndOldRegion(oblast, region);
    }

    public List<VillageCouncil> getAllVillageCouncilsByRegion() {
        String oblast = demoRepository.findAll().get(0).getOblast();
        String region = demoRepository.findAll().get(0).getOldRegion();

        return villageCouncilRepository.findAllByOblastAndOldRegionOrderByImageDescTitleAsc(oblast, region);
    }
}
