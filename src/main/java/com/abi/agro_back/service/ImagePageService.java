package com.abi.agro_back.service;

import com.abi.agro_back.collection.ImagePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImagePageService {
    ImagePage createImagePage(ImagePage imagePage);

    ImagePage getImagePageById(String imagePageId);

    List<ImagePage> getAllImagePages();

    ImagePage updateImagePage(String imagePageId, ImagePage updatedImagePage);

    void deleteImagePage(String  imagePageId);

    Page<ImagePage> findAllByPage(Pageable pageable);

    List<ImagePage> getExhibitionsByKeySearch(String key, String oblast);
}
