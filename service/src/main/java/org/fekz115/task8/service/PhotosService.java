package org.fekz115.task8.service;

import org.fekz115.task8.domain.Photo;
import org.fekz115.task8.domain.Product;
import org.fekz115.task8.repository.PhotosRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class PhotosService {

    private String uploadPath;

    protected final PhotosRepository repository;

    public PhotosService(PhotosRepository repository, String uploadPath) {
        this.uploadPath = uploadPath;
        File file = new File(uploadPath);
        if(!file.exists()) {
            file.mkdirs();
        }
        this.repository = repository;
    }

    public void save(MultipartFile multipartFile, Product product) {
        String fileName = randomizeFileName(multipartFile.getOriginalFilename());
        String resultPath = uploadPath + "/" + fileName;
        File file = new File(resultPath);
        try {
            multipartFile.transferTo(file);
            Photo photo = new Photo();
            photo.setPath(fileName);
            photo.setProduct(product);
            repository.save(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(MultipartFile[] files, Product product) {
        Arrays.stream(files).forEach(x -> save(x, product));
    }

    private String randomizeFileName(String fileName) {
        return UUID.randomUUID() + "." + fileName;
    }

}
