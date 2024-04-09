package com.abi.agro_back.service;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.collection.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PageService {
    Page createPage(Page page);

    Page getPageById(String pageId);

    List<Page> getAllPages();

    org.springframework.data.domain.Page<Page> findAllByPage(Pageable pageable);

    Page updatePage(String pageId, Page updatedPage);

    void deletePage(String  pageId);

}
