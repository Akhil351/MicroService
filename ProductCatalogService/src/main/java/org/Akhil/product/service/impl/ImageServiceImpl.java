package org.Akhil.product.service.impl;

import org.Akhil.common.dto.ImageDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.Image;
import org.Akhil.common.repo.ImageRepo;
import org.Akhil.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepo imageRepo;
    @Override
    public Image getImageById(String id) {
        return imageRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Image Not Found"));
    }

    @Override
    public void deleteImageById(String id) {
          imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,()->{throw  new ResourceNotFoundException("Image Not Found");});
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, String productId) {
        List<ImageDto> images=new ArrayList<>();
        files.forEach(file->{
            Image image=this.saveImage(file,productId);
            images.add(ImageDto.builder().id(image.getId())
                    .fileName(image.getFileName()).build());
        });
        return images;
    }

    private Image saveImage(MultipartFile file,String productId){
        try{
            Image image=Image.builder()
                    .id("im"+UUID.randomUUID().toString())
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .image(new SerialBlob(file.getBytes()))
                    .productId(productId)
                    .build();
            return imageRepo.save(image);
        }
        catch (IOException | SQLException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateImage(MultipartFile file, String imageId) {
       Image image=this.getImageById(imageId);
       try{
           image.setFileName(file.getOriginalFilename());
           image.setFileType(file.getContentType());
           image.setImage(new SerialBlob(file.getBytes()));
           imageRepo.save(image);
       }
       catch (IOException | SQLException e){
           throw new RuntimeException(e);
       }
    }
}
