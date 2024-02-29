package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Agrarian;
import com.abi.agro_back.collection.Note;
import com.abi.agro_back.collection.Photo;
import com.abi.agro_back.collection.User;
import com.abi.agro_back.config.StorageService;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.AgrarianRepository;
import com.abi.agro_back.repository.NoteRepository;
import com.abi.agro_back.repository.UserRepository;
import com.abi.agro_back.service.AgrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class AgrarianServiceImpl implements AgrarianService {

    @Autowired
    private AgrarianRepository agrarianRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private StorageService storageService;

    @Override
    public Agrarian createAgrarian(MultipartFile image, Agrarian agrarian) throws IOException {

        String imageKey = image.getOriginalFilename() + "" + System.currentTimeMillis();
        URL imageUrl = storageService.uploadPhoto(image, imageKey);
        Photo imagePhoto = new Photo(imageKey, imageUrl);
        agrarian.setImage(imagePhoto);

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
        updatedAgrarian.setId(agrarian.getId());

        return agrarianRepository.save(updatedAgrarian);
    }

    @Override
    public void deleteAgrarian(String agrarianId) {

        Agrarian agrarian = agrarianRepository.findById(agrarianId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agrarian is not exists with given id : " + agrarianId));

        agrarianRepository.deleteById(agrarianId);
    }

    @Override
    public List<Agrarian> getAllAgrariansByOblast(String oblast) {
        return agrarianRepository.findAllByOblastOrderByIsPriorityDescTitleAsc(oblast);
    }

    @Override
    public List<Agrarian> getAllAgrariansByPriority() {
        return agrarianRepository.findAllByOrderByIsPriorityDescTitleAsc();
    }

    @Override
    public Note sendNote(Note note) {

        String currentUserName = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userRepository.findByEmail(currentUserName).get();
        note.setUserId(user.getId());
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getUserNotesByAgrarianId(String id) {
        String currentUserName = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userRepository.findByEmail(currentUserName).get();
        List<Note> list = noteRepository.findAllByAgrarianIdIsAndUserIdIsOrderByCreatedAt(id, user.getId());
        return list;
    }

    @Override
    public List<Agrarian> getAllAgrariansByRegion(String oblast, String region) {
        return agrarianRepository.findAllByOblastAndOldRegionOrderByIsPriorityDescTitleAsc(oblast);    }
}
