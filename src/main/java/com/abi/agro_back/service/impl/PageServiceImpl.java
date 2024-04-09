package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Exhibition;
import com.abi.agro_back.collection.Page;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.PageRepository;
import com.abi.agro_back.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Page createPage(Page page) {

        return pageRepository.save(page);
    }

    @Override
    public Page getPageById(String pageId) {

        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new ResourceNotFoundException("Page is not exists with given id : " + pageId));

        return page;
    }

    @Override
    public List<Page> getAllPages() {

        return pageRepository.findAll();
    }

    @Override
    public org.springframework.data.domain.Page<Page> findAllByPage(Pageable pageable) {
        return pageRepository.findAll(pageable);
    }
    @Override
    public Page updatePage(String pageId, Page updatedPage) {

        Page page = pageRepository.findById(pageId).orElseThrow(
                () -> new ResourceNotFoundException("Page is not exists with given id: " + pageId)
        );
        updatedPage.setId(page.getId());

        return pageRepository.save(updatedPage);
    }

    @Override
    public void deletePage(String pageId) {

        Page page = pageRepository.findById(pageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Page is not exists with given id : " + pageId));

        pageRepository.deleteById(pageId);
    }
}
