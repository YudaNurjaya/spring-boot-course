package com.example.demospringboot.service;

import com.example.demospringboot.exception.DataEmptyException;
import com.example.demospringboot.exception.FailedToRunException;
import com.example.demospringboot.exception.NotFoundException;
import com.example.demospringboot.model.CourseType;
import com.example.demospringboot.repository.ICourseTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CourseTypeService {
    @Autowired
    ICourseTypeRepository courseTypeRepository;
    public CourseType save(CourseType courseType){
        try {
            return courseTypeRepository.save(courseType);
        }catch (FailedToRunException e){
            throw new FailedToRunException();
        }
    }
    public Iterable<CourseType> findAll(){
        try {
            return courseTypeRepository.findAll();
        }catch (DataEmptyException e){
            throw new DataEmptyException();
        }
    }
    public Optional<CourseType> findById(Integer id){
        try{
            return courseTypeRepository.findById(id);
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }
    public CourseType update(CourseType courseType, Integer id){
        try {
            Optional<CourseType> find = courseTypeRepository.findById(id);
            find.get().setType(courseType.getType());
            return courseTypeRepository.save(find.get());
        }catch (NotFoundException e){
            throw new NotFoundException();
        }catch (FailedToRunException e){
            throw new FailedToRunException();
        }
    }
}
