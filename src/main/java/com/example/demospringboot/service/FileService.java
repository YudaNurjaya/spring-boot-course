package com.example.demospringboot.service;

import com.example.demospringboot.repository.FileRepository;
import com.example.demospringboot.repository.IFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements IFileService{
    @Autowired
    IFileRepository fileRepository;
    @Override
    public String uploadFile(MultipartFile file) {
        return fileRepository.store(file);
    }

    @Override
    public Resource downloadFile(String fileName) {
        return fileRepository.load(fileName);
    }
}
