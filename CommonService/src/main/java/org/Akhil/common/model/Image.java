package org.Akhil.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {
    @Id
    private String id;
    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String productId;
}
