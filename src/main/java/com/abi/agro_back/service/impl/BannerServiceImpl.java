package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Banner;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.BannerRepository;
import com.abi.agro_back.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner createBanner(Banner banner) {

        Banner savedBanner =bannerRepository.save(banner);

        return savedBanner;
    }

    @Override
    public Banner getBannerById(String bannerId) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner is not exists with given id : " + bannerId));

        return banner;
    }

    @Override
    public List<Banner> getAllBanners() {

        List <Banner> banners = bannerRepository.findAll();
        return banners;
    }

    @Override
    public Banner updateBanner(String bannerId, Banner updatedBanner) {

        Banner banner = bannerRepository.findById(bannerId).orElseThrow(
                () -> new ResourceNotFoundException("Banner is not exists with given id: " + bannerId)
        );

        Banner updatedUserObj = bannerRepository.save(updatedBanner);

        return updatedUserObj;
    }

    @Override
    public void deleteBanner(String bannerId) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Banner is not exists with given id : " + bannerId));

        bannerRepository.deleteById(bannerId);
    }
}
