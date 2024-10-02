package org.Akhil.PRODUCT_SERVICE.service;

import org.Akhil.COMMON_SERVICE.dto.ImageDto;
import org.Akhil.COMMON_SERVICE.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files,Long productId);
    void updateImage(MultipartFile file,Long imageId);
}
