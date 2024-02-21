package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.ImagePage;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.ImagePageRepository;
import com.abi.agro_back.service.ImagePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagePageServiceImpl implements ImagePageService {

    @Autowired
    private ImagePageRepository imagePageRepository;

    @Override
    public ImagePage createImagePage(ImagePage imagePage) {

        return imagePageRepository.save(imagePage);
    }

    @Override
    public ImagePage getImagePageById(String imagePageId) {

        return imagePageRepository.findById(imagePageId)
                .orElseThrow(() -> new ResourceNotFoundException("ImagePage is not exists with given id : " + imagePageId));
    }

    @Override
    public List<ImagePage> getAllImagePages() {

        return imagePageRepository.findAll();
    }

    @Override
    public ImagePage updateImagePage(String imagePageId, ImagePage updatedImagePage) {

        ImagePage imagePage = imagePageRepository.findById(imagePageId).orElseThrow(
                () -> new ResourceNotFoundException("ImagePage is not exists with given id: " + imagePageId)
        );

        return imagePageRepository.save(updatedImagePage);
    }

    @Override
    public void deleteImagePage(String imagePageId) {

       ImagePage imagePage = imagePageRepository.findById(imagePageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ImagePage is not exists with given id : " + imagePageId));

        imagePageRepository.deleteById(imagePageId);
    }

    @Override
    public Page<ImagePage> findAllByPage(Pageable pageable) {
        return imagePageRepository.findAll(pageable);
    }
}
