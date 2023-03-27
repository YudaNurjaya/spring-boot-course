package com.example.demospringboot.model.request;
import com.example.demospringboot.model.CourseType;
import jakarta.validation.Path;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CourseRequest {
    @NotBlank(message = "{invalid.title.required}")
    private String title;
    private String description;
    private Double duration;
    private String level;
    private CourseType courseType;
}
