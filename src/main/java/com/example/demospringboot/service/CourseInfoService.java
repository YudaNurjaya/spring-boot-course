package com.example.demospringboot.service;

import com.example.demospringboot.exception.DataEmptyException;
import com.example.demospringboot.exception.FailedToRunException;
import com.example.demospringboot.exception.NotFoundException;
import com.example.demospringboot.model.CourseInfo;
import com.example.demospringboot.repository.ICourseInfoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CourseInfoService {
    @Autowired
    ICourseInfoRepository courseInfoRepository;
    @Autowired
    ModelMapper modelMapper;
    public CourseInfo save(CourseInfo courseInfo){
        try {
            CourseInfo course = modelMapper.map(courseInfo,CourseInfo.class);
            return courseInfoRepository.save(course);
        }catch (FailedToRunException e){
            throw new FailedToRunException();
        }
    }
    public Iterable<CourseInfo> findAll(){
        try {
            return courseInfoRepository.findAll();
        }catch (DataEmptyException e){
            throw new DataEmptyException();
        }
    }
    public CourseInfo update(CourseInfo courseInfo, Integer id){
        try {
            Optional<CourseInfo> find = courseInfoRepository.findById(id);
            find.get().setDuration(courseInfo.getDuration());
            find.get().setLevel(courseInfo.getLevel());
            return courseInfoRepository.save(find.get());
        }catch (FailedToRunException e){
            throw new FailedToRunException();
        }
    }
    public void delete(Integer id){
        try {
            courseInfoRepository.deleteById(id);
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }
}
