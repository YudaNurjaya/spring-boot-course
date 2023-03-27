package com.example.demospringboot.repository;

import com.example.demospringboot.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseTypeRepository extends JpaRepository<CourseType, Integer> {
}
