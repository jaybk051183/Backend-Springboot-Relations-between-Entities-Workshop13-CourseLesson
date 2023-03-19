package com.example.les13relations.controller;

import com.example.les13relations.dto.CourseDto;
import com.example.les13relations.model.Course;
import com.example.les13relations.model.Teacher;
import com.example.les13relations.repository.CourseRepository;
import com.example.les13relations.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final CourseRepository courseRepos;
    private final TeacherRepository teacherRepos;

    //Use constructor injection to connect the controller layer with the repository layer . In this case no service layer is being used for the purpose of the assignment.`This grants access to the teacher and course entity in the database.
    public CourseController(CourseRepository courseRepos, TeacherRepository teacherRepos) {
        this.courseRepos = courseRepos;
        this.teacherRepos = teacherRepos;
    }

    //Now repo is created, use mapping to translate from  DTO to entity.

    //Create a PostMapping which returns  a DTO object when applying the method createCourse and creates Course entities in the Database.

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        //Make a new course object
        Course course = new Course();

        // Mapping: conversion of DTO to entity and translation of fields: title & sp
        course.setTitle(courseDto.title);
        course.setSp(courseDto.sp);

        //Obtain the Id of teacher from the database which is requested by the client through the DTO object:
        Teacher teacher = teacherRepos.findById(courseDto.teacherId).get(); // happy flow
        //Once teacher Id has been obtained the link between the course object and teacher object can be finalized with:
        course.setTeacher(teacher);
        //SetTeacher will set the value of the field teacher in Course.java class. and save it as a foreign key under the column teacher_Id.
        //The id will be used in the course object to set a link with a teacher object.

        //Storage: store  the entity in the database:
        courseRepos.save(course);
        //The course entity which is saved automatically receives an Id which can be retrieved with the following:
        courseDto.id = course.getId()
        return new ResponseEntity<>(courseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOptional = courseRepos.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseDto courseDto = new CourseDto();
            courseDto.id = course.getId();
            courseDto.title = course.getTitle();
            courseDto.sp = course.getSp();
            courseDto.teacherId = course.getTeacher().getId();

            return new ResponseEntity<>(courseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
