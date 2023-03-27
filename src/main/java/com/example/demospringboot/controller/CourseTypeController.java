package com.example.demospringboot.controller;

import com.example.demospringboot.model.CourseType;
import com.example.demospringboot.model.request.CourseTypeRequest;
import com.example.demospringboot.model.response.SuccessResponse;
import com.example.demospringboot.service.CourseTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/coursetype")
public class CourseTypeController {
    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity findAll(){
        Iterable<CourseType> get = courseTypeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Iterable<CourseType>>("Success",get));
    }
    @PostMapping
    public ResponseEntity save(@RequestBody CourseTypeRequest courseTypeRequest){
        CourseType map = modelMapper.map(courseTypeRequest,CourseType.class);
        CourseType save = courseTypeService.save(map);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<CourseType>("Created",save));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody CourseTypeRequest courseTypeRequest, @PathVariable Integer id){
        CourseType courseType = modelMapper.map(courseTypeRequest,CourseType.class);
        CourseType update = courseTypeService.update(courseType,id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<CourseType>("Updated",update));
    }
}
