package com.example.les13relations.controller;

import com.example.les13relations.dto.LessonDto;
import com.example.les13relations.model.Course;
import com.example.les13relations.model.Lesson;
import com.example.les13relations.repository.CourseRepository;
import com.example.les13relations.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("lessons")
public class LessonController {
    private final CourseRepository courseRepos;
    private final LessonRepository lessonRepos;

    public LessonController(CourseRepository courseRepos, LessonRepository lessonRepos) {
        this.courseRepos = courseRepos;
        this.lessonRepos = lessonRepos;
    }

    @PostMapping
    public ResponseEntity<LessonDto>createLesson(@RequestBody LessonDto lessonDto) {
        Lesson lesson = new Lesson();

        lesson.setTopic(lessonDto.topic);

        Course course = courseRepos.findById(lessonDto.courseId).get();

        lesson.setCourse(course);

        lessonRepos.save(lesson);

        lessonDto.id = lesson.getId();

return new ResponseEntity<>(lessonDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        Optional<Lesson> lessonOptional = lessonRepos.findById(id);
        if (lessonOptional.isPresent()) {
            Lesson lesson = lessonOptional.get();
            LessonDto lessonDto = new LessonDto();
            lessonDto.id = lesson.getId();
            lessonDto.topic = lesson.getTopic();
            lessonDto.courseId = lesson.getCourse().getId();

            return new ResponseEntity<>(lessonDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
