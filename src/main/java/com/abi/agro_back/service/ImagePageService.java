package com.abi.agro_back.service;

import com.abi.agro_back.collection.ImagePage;

import java.util.List;

public interface ImagePageService {
    ImagePage createImagePage(ImagePage imagePage);

    ImagePage getImagePageById(String imagePageId);

    List<ImagePage> getAllImagePages();

    ImagePage updateImagePage(String imagePageId, ImagePage updatedImagePage);

    void deleteImagePage(String  imagePageId);

}
