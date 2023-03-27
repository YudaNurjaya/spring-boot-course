package com.example.demospringboot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "course")
@Data
@Getter @Setter
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "link")
    private String link;
    @OneToOne(orphanRemoval = true)
    private CourseInfo courseInfo;
    @ManyToOne
    private CourseType courseType;
}
