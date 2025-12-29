package com.myproject.thelensoryco.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
            final Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());

            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }

    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Image deletion failed");
        }
    }

    // helper method
    private String extractPublicId(String imageUrl) {
        // Example URL:
        // https://res.cloudinary.com/demo/image/upload/v123456/photos/sample.jpg
        String withoutExtension = imageUrl.substring(0, imageUrl.lastIndexOf('.'));
        return withoutExtension.substring(withoutExtension.lastIndexOf('/') + 1);
    }
}
