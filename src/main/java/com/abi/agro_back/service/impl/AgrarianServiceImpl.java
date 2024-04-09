package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.*;
import com.abi.agro_back.config.StorageService;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.AgrarianRepository;
import com.abi.agro_back.repository.NoteRepository;
import com.abi.agro_back.repository.UserRepository;
import com.abi.agro_back.repository.VillageCouncilRepository;
import com.abi.agro_back.service.AgrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private VillageCouncilRepository villageCouncilRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private MongoTemplate mongoTemplate;

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
        return agrarianRepository.findAllByOblastAndIsForOblastOnlyTrueOrderByIsPriorityDescTitleAsc(oblast);
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
    public Page<Agrarian> getAllAgrariansByRegion(String oblast, String region, Pageable pageable) {
        return agrarianRepository.findAllByOblastAndOldRegionOrderByImageDescTitleAsc(oblast, region, pageable);
    }

    @Override
    public long getCountAllAgrarians() {
        long count = villageCouncilRepository.count() + agrarianRepository.count();
        return count;
    }

    @Override
    public long getCountAgrariansByOblast(String oblast) {
        long count = villageCouncilRepository.countAllByOblastEquals(oblast) + agrarianRepository.countAllByOblastEquals(oblast);
        return count;
    }

    @Override
    public long getCountAgrariansByRegion(String oblast, String region) {
        long count = villageCouncilRepository.countAllByOblastEqualsAndOldRegionEquals(oblast, region) + agrarianRepository.countAllByOblastEqualsAndOldRegionEquals(oblast, region);
        return count;
    }
}
