package com.example.demospringboot.service;

import com.example.demospringboot.exception.DataEmptyException;
import com.example.demospringboot.exception.FailedToRunException;
import com.example.demospringboot.exception.NotFoundException;
import com.example.demospringboot.model.Course;
import com.example.demospringboot.model.CourseInfo;
import com.example.demospringboot.model.CourseType;
import com.example.demospringboot.model.request.CourseInfoRequest;
import com.example.demospringboot.model.request.CourseRequest;
import com.example.demospringboot.repository.ICourseRepository;
import com.example.demospringboot.utils.CourseKey;
import com.example.demospringboot.utils.specification.SearchCriteria;
import com.example.demospringboot.utils.specification.Spec;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    @Autowired
    ICourseRepository courseRepository;
    @Autowired
    CourseInfoService courseInfoService;
    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    IFileService fileService;
    @Autowired
    ModelMapper modelMapper;

    public Iterable<Course> list(Pageable pageable) {
        try {
            return courseRepository.findAll(pageable);
        } catch (DataEmptyException e) {
            throw new DataEmptyException();
        }
    }

    public Course create(String course, MultipartFile file) {
        try {
            CourseRequest map = new ObjectMapper().readValue(course,CourseRequest.class);
            CourseInfo courseInfo = modelMapper.map(map,CourseInfo.class);
            CourseInfo set = courseInfoService.save(courseInfo);
            Optional<CourseType> find = courseTypeService.findById(map.getCourseType().getId());
            Course save = modelMapper.map(map, Course.class);
            String filePath = fileService.uploadFile(file);
            save.setCourseType(find.get());
            save.setCourseInfo(set);
            save.setLink(filePath);
            System.out.println(save);
            return courseRepository.save(save);
        } catch (FailedToRunException e) {
            throw new FailedToRunException();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Course> getById(Integer id) {
        try {
            return courseRepository.findById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException();
        }

    }

    public Course update(Course course, Integer id) {
        try {
            Optional<Course> find = courseRepository.findById(id);
            find.get().setTitle(course.getTitle());
            find.get().setDescription(course.getDescription());
            find.get().setLink(course.getLink());
            find.get().setCourseInfo(course.getCourseInfo());
            find.get().setCourseType(course.getCourseType());
            return courseRepository.save(find.get());
        } catch (FailedToRunException e) {
            throw new FailedToRunException();
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }

    public void delete(Integer id) {
        try {
            courseRepository.deleteById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException();
        }
    }
    private List<Course> findByTitleContains(String value) {
        List<Course> courses = courseRepository.findByTitleContains(value);
        if (courses.isEmpty()) {
            throw new NotFoundException("Course with " + value + " title is not found");
        }

        return courses;
    }

    private List<Course> findByDescriptionContains(String value) {
        List<Course> courses = courseRepository.findByDescriptionContains(value);
        if (courses.isEmpty()) {
            throw new NotFoundException("Course with " + value + " description is not found");
        }

        return courses;
    }
    public List<Course> getBy(CourseKey key, String val){
        return switch (key){
            case title -> findByTitleContains(val);
            case description -> findByDescriptionContains(val);
            default -> courseRepository.findAll();
        };
    }
    public List<Course> listBy(SearchCriteria searchCriteria) {
        Specification specification = new Spec<Course>().findBy(searchCriteria);
        List<Course> courses = courseRepository.findAll(specification);
        return courses;
    }
//    public Course getJson(Course course, MultipartFile file){
//        Course courseJson = new Course();
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            courseJson = objectMapper.readValue(course,Course.class);
//            return courseJson;
//        }catch (IOException e){
//            throw new RuntimeException("Error " + e.getMessage());
//        }
//    }
//    public Resource downloadById(Integer id){
//        try {
//            Optional<Course> find = courseTypeService.findById(id);
//            Resource file = new UrlResource();
//            return file;
//        }catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }
}
