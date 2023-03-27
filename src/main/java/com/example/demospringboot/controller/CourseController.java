package com.example.demospringboot.controller;
import com.example.demospringboot.model.Course;
import com.example.demospringboot.model.request.CourseRequest;
import com.example.demospringboot.model.request.FormDataWithFile;
import com.example.demospringboot.model.response.SuccessResponse;
import com.example.demospringboot.service.CourseService;
import com.example.demospringboot.service.IFileService;
import com.example.demospringboot.utils.constans.Operator;
import com.example.demospringboot.utils.specification.SearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@Validated
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    IFileService fileService;
    @Autowired
    ModelMapper modelMapper;
    @GetMapping("/{size}/{page}/{sort}")
    public ResponseEntity getAllCourse(@PathVariable int size, @PathVariable int page, @PathVariable String sort) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("id").ascending());
        if(sort.equals("desc")){
            pageable = PageRequest.of(page-1,size, Sort.by("id").descending());
        }
        Iterable<Course> list = courseService.list(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Iterable<Course>>("Success", list));
    }

    @PostMapping
    public ResponseEntity create(@RequestPart("course") String course,@RequestPart("file") MultipartFile file){
        Course create = courseService.create(course, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Course>("Success", create));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        Optional<Course> result = courseService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Optional<Course>>("Success", result));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        courseService.delete(id);
        Optional<Course> delete = courseService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Optional<Course>>("Success", delete));
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody CourseRequest course, @PathVariable Integer id) {
        Course insert = modelMapper.map(course,Course.class);
        Course update = courseService.update(insert, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Course>("Success", update));

    }
    @GetMapping(params = {"key", "value", "operator"})
    public ResponseEntity getAllBy(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("operator") String operator) throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria(key, Operator.valueOf(operator), value);
        List<Course> courses = courseService.listBy(searchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success get all course by", courses));
    }
}
