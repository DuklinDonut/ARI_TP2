package com.example.student_stages.stage;

import com.example.student_stages.student.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idStage;
    private String title;
    private String description;

    public Stage() {}

    public Stage(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Stage[idStage=%d, title='%s', description='%s']",
                idStage, title, description);
    }

    public Long getId() {
        return idStage;
    }

    // Bidirectional relationship to Student
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setIdStage(Long idStage) {
        this.idStage = idStage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}