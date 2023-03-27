package com.example.demospringboot.service;

import com.example.demospringboot.model.ImageData;
import com.example.demospringboot.repository.StorageDataRepository;
import com.example.demospringboot.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class StorageDataService {
    @Autowired
    StorageDataRepository dataRepository;
    public String uploadFile(MultipartFile file) throws IOException {
        ImageData imageData = dataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
        if(imageData != null){
            return "File Uploaded " + file.getOriginalFilename();
        }
        return null;
    }
    public byte[] downloadFile(String fileName){
        Optional<ImageData> file = dataRepository.findByName(fileName);
        System.out.println(file);
        byte[] data = ImageUtils.decompressImage(file.get().getImageData());
        System.out.println(data);
        return data;
    }
}
