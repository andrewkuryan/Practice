package org.fekz115.task8.service;

import org.fekz115.task8.repository.PhotosRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringPhotosService extends PhotosService {
    public SpringPhotosService(PhotosRepository repository, String uploadPath) {
        super(repository, uploadPath);
    }
}
