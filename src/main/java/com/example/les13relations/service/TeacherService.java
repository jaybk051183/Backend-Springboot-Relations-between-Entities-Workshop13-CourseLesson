package com.example.les13relations.service;

import com.example.les13relations.dto.TeacherDto;
import com.example.les13relations.exception.ResourceNotFoundException;
import com.example.les13relations.model.Course;
import com.example.les13relations.model.Teacher;
import com.example.les13relations.repository.TeacherRepository;
import org.springframework.stereotype.Service;

//Define the service class
@Service
public class TeacherService {

    //Declare a private final variable of the TeacherRepository type
    private final TeacherRepository repos;

    //Define the constructor  for the service class which has a TeacherRepository-object as parameter
    public TeacherService(TeacherRepository repos) {
        //Initialize the 'repos'-variable with the given TeacherRepository-object
        this.repos = repos;
    }

    //Defina a method to create a new teacher through a TeacherDto-object
    public Long createTeacher(TeacherDto tdto) {

        // Mapping: Create a new Teacher-object and copy the values of the given TeacherDto object
        Teacher t = new Teacher();
        t.setFirstName(tdto.firstName);
        t.setLastName(tdto.lastName);
        t.setDob(tdto.dob);

        // Store the new Teacher-object  in the repository
        repos.save(t);

        //Return the ID of the previously stored Teacher-object
        return t.getId();
    }

    //Define a method to retrieve a TeacherDto-object based on the given ID by the client
    public TeacherDto getTeacher(Long id) {
        //Define a method which searches a teacher with the given ID
        Teacher t = repos.findById(id)
                //If no teacher found then throw a ResourceNotFoundException
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        // Mapping: Create a new TeacherDto-object and copy the values of the Teacher-object found
        TeacherDto tdto = new TeacherDto();
        tdto.id = t.getId();
        tdto.firstName = t.getFirstName();
        tdto.lastName = t.getLastName();
        tdto.dob = t.getDob();

        //Iterate through a list of courses which as coupled with the teacher
        for (Course c : t.getCourses()) {
            //Add the tile of each course to the 'courseTitles'-list in the TeacherDto-object
            tdto.courseTitles.add(c.getTitle());
        }
        //Finally, return the injected and completed TeacherDto-object
        return tdto;
    }
}
