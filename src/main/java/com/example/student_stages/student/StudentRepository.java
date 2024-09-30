package com.example.student_stages.student;

import java.util.List;

import com.example.student_stages.student.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findStudentByLastName(String lastName);

    Student findStudentById(long idStudent);
}