package org.Akhil.product.controller;

import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.dto.ImageDto;
import org.Akhil.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v2/images")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile>files, @PathVariable Long productId){
            List<ImageDto> images=imageService.saveImage(files,productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Image Upload Success").data(images).build());
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam MultipartFile file){
            imageService.updateImage(file,imageId);
            return ResponseEntity.ok(ApiResponse.builder().message("Update Success").data(null).build());
    }

    @DeleteMapping("/deleteImage/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok(ApiResponse.builder().message("Delete Success").data(null).build());
    }
}
