package com.example.demospringboot.controller;

import com.example.demospringboot.model.response.SuccessResponse;
import com.example.demospringboot.service.StorageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    StorageDataService dataService;
    @PostMapping
    public ResponseEntity uploadFile(@RequestParam("image") MultipartFile file) throws IOException {
        String data = dataService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<String>("File Uploaded",data));
    }
    @GetMapping("/{name}")
    public ResponseEntity downloadFile(@PathVariable String name){
        byte[] file = dataService.downloadFile(name);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(file);
    }
}
