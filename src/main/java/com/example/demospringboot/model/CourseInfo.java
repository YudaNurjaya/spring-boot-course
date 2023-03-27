package com.example.demospringboot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_info")
@Data
@Getter @Setter
public class CourseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "duration")
    private Double duration;
    @Column(name = "level")
    private String level;
}
