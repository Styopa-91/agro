package com.abi.agro_back.service;

import com.abi.agro_back.collection.Page;

import java.util.List;

public interface PageService {
    Page createPage(Page page);

    Page getPageById(String pageId);

    List<Page> getAllPages();

    Page updatePage(String pageId, Page updatedPage);

    void deletePage(String  pageId);

}
