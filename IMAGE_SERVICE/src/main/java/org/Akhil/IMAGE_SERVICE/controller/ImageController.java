package org.Akhil.IMAGE_SERVICE.controller;

import org.Akhil.COMMON_SERVICE.dto.ApiResponse;
import org.Akhil.COMMON_SERVICE.dto.ImageDto;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.Akhil.IMAGE_SERVICE.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
