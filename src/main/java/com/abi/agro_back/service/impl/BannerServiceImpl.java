package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Banner;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.BannerRepository;
import com.abi.agro_back.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner createBanner(Banner banner) {

        return bannerRepository.save(banner);
    }

    @Override
    public Banner getBannerById(String bannerId) {

        return bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner is not exists with given id : " + bannerId));
    }

    @Override
    public List<Banner> getAllBanners() {

        return bannerRepository.findAll();
    }

    @Override
    public Banner updateBanner(String bannerId, Banner updatedBanner) {

        Banner banner = bannerRepository.findById(bannerId).orElseThrow(
                () -> new ResourceNotFoundException("Banner is not exists with given id: " + bannerId)
        );

        return bannerRepository.save(updatedBanner);
    }

    @Override
    public void deleteBanner(String bannerId) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Banner is not exists with given id : " + bannerId));

        bannerRepository.deleteById(bannerId);
    }

    @Override
    public Page<Banner> findAllByPage(Pageable pageable) {
        return bannerRepository.findAll(pageable);
    }
}
