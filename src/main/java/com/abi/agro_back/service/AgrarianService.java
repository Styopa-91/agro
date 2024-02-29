package com.abi.agro_back.service;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.collection.Note;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AgrarianService {
    Agrarian createAgrarian(MultipartFile image, Agrarian agrarian) throws IOException;

    Agrarian getAgrarianById(String agrarianId);

    List<Agrarian> getAllAgrarians();

    Agrarian updateAgrarian(String agrarianId, Agrarian updatedAgrarian);

    void deleteAgrarian(String  agrarianId);

    List<Agrarian> getAllAgrariansByOblast(String oblast);

    List<Agrarian> getAllAgrariansByPriority();

    Note sendNote(Note note);

    List<Note> getUserNotesByAgrarianId(String id);

    List<Agrarian> getAllAgrariansByRegion(String oblast, String region);
}
