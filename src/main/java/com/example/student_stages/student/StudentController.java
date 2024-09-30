package com.example.student_stages.student;

import com.example.student_stages.stage.Stage;
import com.example.student_stages.stage.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StageRepository stageRepository;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    // Get a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new student with an associated stage using stageId
    @PostMapping("/byId")
    public Student createStudentById(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Long stageId // Parameter to link a stage by its ID
    ) {
        // Retrieve the stage by its ID
        Stage stage = stageRepository.findStageById(stageId);

        // Create and save the student with the associated stage
        Student student = new Student(firstName, lastName, stage);
        return studentRepository.save(student);
    }

    @PostMapping("/byTitle")
    public Student createStudentByTitle(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String title // Parameter to link a stage by its title
    ) {
        // Retrieve or create a stage based on the title
        Stage stage = (Stage) stageRepository.findStageByTitle(title);

        if (stage == null) {
            // If the stage doesn't exist, create a new one
            stage = new Stage();
            stage.setTitle(title);
            stageRepository.save(stage);
        }

        // Create and save the student with the associated stage
        Student student = new Student(firstName, lastName, stage);
        return studentRepository.save(student);
    }


    // Update a student by ID
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setStage(updatedStudent.getStage()); // Update the stage if necessary
            studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
