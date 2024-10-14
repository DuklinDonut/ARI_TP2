package com.example.student_stages.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private static StudentRepository studentRepository;

    public static Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(existingStudent -> {
            existingStudent.setFirstName(studentDetails.getFirstName());
            existingStudent.setLastName(studentDetails.getLastName());
            existingStudent.setStage(studentDetails.getStage());
            // Update other fields as necessary
            return studentRepository.save(existingStudent);
        }).orElseGet(() -> {
            // Print a message when the student is not found
            System.out.println("Student not found with id " + id);
            return null; // Return null if the student is not found
        });
    }
}
