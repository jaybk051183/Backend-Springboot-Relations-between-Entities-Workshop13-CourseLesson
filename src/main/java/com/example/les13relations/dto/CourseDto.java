package com.example.les13relations.dto;

public class CourseDto {

    public Long id;

    public String title;

    public int sp;

    //Provide the client the possibility to add a teacherId when creating a new course:
    public Long teacherId;
    //Then connect the teacher with course in the course-controller class.
}
