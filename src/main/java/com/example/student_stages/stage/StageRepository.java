package com.example.student_stages.stage;

import java.util.List;

import com.example.student_stages.student.Student;
import org.springframework.data.repository.CrudRepository;

public interface StageRepository extends CrudRepository<Stage, Long> {

    Stage findStageByTitle(String title);
    Stage findStageById(long idStage);
}