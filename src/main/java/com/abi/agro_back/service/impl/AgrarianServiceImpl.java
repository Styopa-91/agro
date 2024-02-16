package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.AgrarianRepository;
import com.abi.agro_back.service.AgrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgrarianServiceImpl implements AgrarianService {

    @Autowired
    private AgrarianRepository agrarianRepository;

    @Override
    public Agrarian createAgrarian(Agrarian agrarian) {

        return agrarianRepository.save(agrarian);
    }

    @Override
    public Agrarian getAgrarianById(String agrarianId) {

        return agrarianRepository.findById(agrarianId)
                .orElseThrow(() -> new ResourceNotFoundException("Agrarian is not exists with given id : " + agrarianId));
    }

    @Override
    public List<Agrarian> getAllAgrarians() {

        return agrarianRepository.findAll();
    }

    @Override
    public Agrarian updateAgrarian(String agrarianId, Agrarian updatedAgrarian) {

        Agrarian agrarian = agrarianRepository.findById(agrarianId).orElseThrow(
                () -> new ResourceNotFoundException("Agrarian is not exists with given id: " + agrarianId)
        );

        return agrarianRepository.save(updatedAgrarian);
    }

    @Override
    public void deleteAgrarian(String agrarianId) {

        Agrarian agrarian = agrarianRepository.findById(agrarianId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agrarian is not exists with given id : " + agrarianId));

        agrarianRepository.deleteById(agrarianId);
    }
}
