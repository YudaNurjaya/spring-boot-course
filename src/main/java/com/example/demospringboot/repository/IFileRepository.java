package com.example.demospringboot.repository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface IFileRepository {
    String store(MultipartFile file);
    Resource load(String fileName);

}
