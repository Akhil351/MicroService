package org.Akhil.product.service;

import org.Akhil.common.dto.ImageDto;
import org.Akhil.common.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(String id);
    void deleteImageById(String id);
    List<ImageDto> saveImage(List<MultipartFile> files,String productId);
    void updateImage(MultipartFile file,String imageId);
}
