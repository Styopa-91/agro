package com.abi.agro_back.service;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.collection.VillageCouncil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DemoService {

    Page<Agrarian> getAllAgrariansByRegion(Pageable pageable);
    List<Agrarian> getAllExcelAgrarians(String obl, String reg);
    List<VillageCouncil> getAllExcelVillageCouncils(String obl, String reg);
    List<Agrarian> getAllExcelOblAgrarians(String obl);
    List<VillageCouncil> getAllExcelOblVillageCouncils(String obl);
    List<Agrarian> getAllDemoAgrarians();
    List<VillageCouncil> getAllVillageCouncilsByRegion();
}
