package com.example.demospringboot.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class FormDataWithFile {
    private MultipartFile file;
}
