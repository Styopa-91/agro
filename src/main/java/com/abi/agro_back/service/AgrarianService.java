package com.abi.agro_back.service;

import com.abi.agro_back.collection.Agrarian;

import java.util.List;

public interface AgrarianService {
    Agrarian createAgrarian(Agrarian agrarian);

    Agrarian getAgrarianById(String agrarianId);

    List<Agrarian> getAllAgrarians();

    Agrarian updateAgrarian(String agrarianId, Agrarian updatedAgrarian);

    void deleteAgrarian(String  agrarianId);

}
