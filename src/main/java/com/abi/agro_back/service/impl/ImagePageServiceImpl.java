package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.ImagePage;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.ImagePageRepository;
import com.abi.agro_back.service.ImagePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagePageServiceImpl implements ImagePageService {

    @Autowired
    private ImagePageRepository imagePageRepository;

    @Override
    public ImagePage createImagePage(ImagePage imagePage) {

        ImagePage savedImagePage =imagePageRepository.save(imagePage);

        return savedImagePage;
    }

    @Override
    public ImagePage getImagePageById(String imagePageId) {

        ImagePage imagePage = imagePageRepository.findById(imagePageId)
                .orElseThrow(() -> new ResourceNotFoundException("ImagePage is not exists with given id : " + imagePageId));

        return imagePage;
    }

    @Override
    public List<ImagePage> getAllImagePages() {

        List <ImagePage> imagePages = imagePageRepository.findAll();
        return imagePages;
    }

    @Override
    public ImagePage updateImagePage(String imagePageId, ImagePage updatedImagePage) {

        ImagePage imagePage = imagePageRepository.findById(imagePageId).orElseThrow(
                () -> new ResourceNotFoundException("ImagePage is not exists with given id: " + imagePageId)
        );

        ImagePage updatedImagePageObj = imagePageRepository.save(updatedImagePage);

        return updatedImagePageObj;
    }

    @Override
    public void deleteImagePage(String imagePageId) {

       ImagePage imagePage = imagePageRepository.findById(imagePageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ImagePage is not exists with given id : " + imagePageId));

        imagePageRepository.deleteById(imagePageId);
    }
}
