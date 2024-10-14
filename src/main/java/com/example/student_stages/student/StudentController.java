package com.example.student_stages.student;

import com.example.student_stages.stage.Stage;
import com.example.student_stages.stage.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin("http://localhost:3000/")
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

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }
    //UPDATE : à retravailler
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        if (!studentRepository.existsById(id)) {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }

        // Get the existing student
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent != null) {
            // Update fields
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());

            // Get the stage by ID if provided
            if (updatedStudent.getStage() != null && updatedStudent.getStage().getId() != null) {
                Stage stage = stageRepository.findById(updatedStudent.getStage().getId()).orElse(null);
                if (stage != null) {
                    existingStudent.setStage(stage); // Update the existing student's stage
                }
            }

            studentRepository.save(existingStudent);
        }

        return new ResponseEntity<>("Student updated successfully", HttpStatus.OK);
    }




















}




