package com.example.student_stages;

import com.example.student_stages.student.Student;
import com.example.student_stages.student.StudentRepository;
import com.example.student_stages.stage.Stage;
import com.example.student_stages.stage.StageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentStagesApplication {

    // Add SLF4J logger
    private static final Logger log = LoggerFactory.getLogger(StudentStagesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StudentStagesApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentRepository studentRepository, StageRepository stageRepository) {
        return (args) -> {
            // Create a few stages (internships)
            Stage stage1 = new Stage("Software Engineering Intern", "Develop software features for an internal tool.");
            Stage stage2 = new Stage("Data Analyst Intern", "Analyze company data and generate reports.");
            stageRepository.save(stage1);
            stageRepository.save(stage2);

            // Save a few students and associate them with stages
            studentRepository.save(new Student("Jack", "Bauer", stage1));
            studentRepository.save(new Student("Chloe", "O'Brian", stage1));  // Jack and Chloe share the same stage
            studentRepository.save(new Student("Kim", "Bauer", stage2));      // Kim is associated with a different stage
            studentRepository.save(new Student("David", "Palmer", null));     // David has no stage
            studentRepository.save(new Student("Michelle", "Dessler", null)); // Michelle has no stage

            // Fetch all students
            log.info("Students found with findAll():");
            log.info("-------------------------------");
            studentRepository.findAll().forEach(student -> {
                log.info(student.toString());
            });
            log.info("");

            // Fetch an individual student by ID
            Student student = studentRepository.findStudentById(1L);
            if (student != null) {
                log.info("Student found with findById(1L):");
                log.info("--------------------------------");
                log.info(student.toString());
                log.info("");
            } else {
                log.warn("Student with ID 1 not found.");
            }

            // Fetch students by last name
            log.info("Students found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            studentRepository.findStudentByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");

            // Fetch all stages
            log.info("Stages found with findAll():");
            log.info("-------------------------------");
            stageRepository.findAll().forEach(stage -> {
                log.info(stage.toString());
            });
            log.info("");
        };
    }
}
