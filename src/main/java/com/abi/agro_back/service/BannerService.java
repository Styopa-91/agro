package com.abi.agro_back.service;

import com.abi.agro_back.collection.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    Banner createBanner(MultipartFile image, Banner banner) throws IOException;

    Banner getBannerById(String bannerId);

//    List<Banner> getAllBanners();

    Banner updateBanner(String bannerId, Banner updatedBanner);

    void deleteBanner(String  bannerId);

//    Page<Banner> findAllByPage(Pageable pageable);

    List<Banner> getBannersByKeySearch(String key, String oblast);

    List<Banner> getAllBannersByOblast(String oblast);
}
