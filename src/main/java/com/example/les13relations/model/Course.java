package com.example.les13relations.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int sp;

    //Add a field for teacher  to set the relation with the Teacher class.

    //Course is the many side of the relation with Teacher.

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    @ManyToOne

    //Use annotation to create the foreign key (teacher_id)
    @JoinColumn(name = "teacher_id")

    //Define the field for Teacher.
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}



