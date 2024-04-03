package com.abi.agro_back.service;

import com.abi.agro_back.collection.ImagePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ImagePageService {
    ImagePage createImagePage(ImagePage imagePage) throws IOException;

    ImagePage getImagePageById(String imagePageId);

    List<ImagePage> getAllImagePages();

    ImagePage updateImagePage(String imagePageId, ImagePage updatedImagePage);

    void deleteImagePage(String  imagePageId);

    Page<ImagePage> findAllByPage(Pageable pageable);

    List<ImagePage> getImagePagesByKeySearch(String key);
}
