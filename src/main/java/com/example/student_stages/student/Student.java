package com.example.student_stages.student;

import jakarta.persistence.*;
import com.example.student_stages.stage.Stage;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idStudent;
    private String firstName;
    private String lastName;

    // Define the Many-to-One relationship with Stage
    @ManyToOne
    @JoinColumn(name = "stage_id") // stage_id will be the foreign key column in the student table
    private Stage stage;

    protected Student() {}

    public Student(String firstName, String lastName, Stage stage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.stage = stage;
    }

    @Override
    public String toString() {
        return String.format(
                "Student[idStudent=%d, firstName='%s', lastName='%s', stage='%s']",
                idStudent, firstName, lastName, stage != null ? stage.getTitle() : "No Stage");
    }

    public Long getId() {
        return idStudent;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
