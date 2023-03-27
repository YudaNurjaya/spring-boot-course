package com.example.demospringboot.controller;

import com.example.demospringboot.model.request.FormDataWithFile;
import com.example.demospringboot.model.response.SuccessResponse;
import com.example.demospringboot.service.IFileService;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private IFileService fileService;

    @PostMapping
    public ResponseEntity upload(FormDataWithFile formDataWithFile) {
        MultipartFile file = formDataWithFile.getFile();
        String filePath = fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<String>("File Uploaded", filePath));
    }

    @GetMapping
    public ResponseEntity download(@RequestParam String fileName) throws MalformedJwtException {
        Resource file = fileService.downloadFile(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attechment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/{filename}")
    public ResponseEntity getFile(@PathVariable("filename") String filename) throws IOException {
        Resource file = fileService.downloadFile(filename);

        byte[] data = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(data);
    }
}
