package com.example.demospringboot.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileRepository implements IFileRepository{
    private final Path root;
    @Autowired
    public FileRepository(@Value("${assets_path}") String rootpath) {
        this.root = Paths.get(rootpath);
    }

    @Override
    public String store(MultipartFile file) {
        try {
            Path filePath = root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString();
        }catch (Exception e){
            throw new RuntimeException("Could not store the file. Error " + e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {
     try {
         Path filePath = root.resolve(fileName);

         Resource resource = new UrlResource(filePath.toUri());
         if(resource.exists()){
             return resource;
         }else {
             throw new RuntimeException("Could not read the file");
         }
     }catch (MalformedURLException e) {
         throw new RuntimeException(e);
     }
    }

}
