package org.Akhil.product.controller;

import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.dto.ImageDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v2/images")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile>files, @PathVariable Long productId){
        try{
            List<ImageDto> images=imageService.saveImage(files,productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Image Upload Success").data(images).build());
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Upload Failed").data(e.getMessage()).build());
        }
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam MultipartFile file){
        try{
            imageService.updateImage(file,imageId);
            return ResponseEntity.ok(ApiResponse.builder().message("Update Success").data(null).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Upload Failed").data(e.getMessage()).build());
        }
    }

    @DeleteMapping("/deleteImage/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try{
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok(ApiResponse.builder().message("Delete Success").data(null).build());
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder().message(e.getMessage()).data(null).build());
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Upload Failed").data(e.getMessage()).build());
        }
    }
}
