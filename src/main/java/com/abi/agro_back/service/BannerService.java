package com.abi.agro_back.service;

import com.abi.agro_back.collection.Banner;

import java.util.List;

public interface BannerService {
    Banner createBanner(Banner banner);

    Banner getBannerById(String bannerId);

    List<Banner> getAllBanners();

    Banner updateBanner(String bannerId, Banner updatedBanner);

    void deleteBanner(String  bannerId);

}
