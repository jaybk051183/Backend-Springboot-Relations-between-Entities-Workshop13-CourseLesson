package com.example.les13relations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Declare the TeacherDto class
public class TeacherDto {
    //Create the required fields and add restrictions
    public Long id;

    @NotBlank
    public String firstName;

    @Size(min = 3, max = 255)
    public String lastName;

    @Past
    public LocalDate dob;

    //Vice versa can be done as well: provide a field in the TeacherDTO object for the course. In this case the courseTitle.
    public List<String> courseTitles = new ArrayList<>();
}
