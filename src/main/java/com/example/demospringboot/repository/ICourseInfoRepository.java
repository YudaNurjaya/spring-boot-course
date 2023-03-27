package com.example.demospringboot.repository;

import com.example.demospringboot.model.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseInfoRepository extends JpaRepository<CourseInfo, Integer> {
}
