package com.example.student_stages.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stages")
public class StageController {

    @Autowired
    private StageRepository stageRepository;

    // Get all stages
    @GetMapping
    public List<Stage> getAllStages() {
        return (List<Stage>) stageRepository.findAll();
    }

    // Get a stage by ID
    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable Long id) {
        Optional<Stage> stage = stageRepository.findById(id);
        if (stage.isPresent()) {
            return ResponseEntity.ok(stage.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Create a new stage (GET or POST) with query params like ?title=Intern&description=Develop
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public Stage createStage(@RequestParam String title, @RequestParam String description) {
        Stage stage = new Stage(title, description);
        return stageRepository.save(stage);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable Long id, @RequestBody Stage updatedStage) {
        if (!stageRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedStage.setIdStage(id); // Assure-toi de mettre à jour l'ID pour éviter d'en créer un nouveau
        return new ResponseEntity<>(stageRepository.save(updatedStage), HttpStatus.OK);
    }

}
    /*

    // Create a new stage
    @PostMapping
    public Stage createStage(@RequestBody Stage stage) {
        return stageRepository.save(stage);
    }

    // Update a stage by ID
    @PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable Long id, @RequestBody Stage updatedStage) {
        Optional<Stage> stageOptional = stageRepository.findById(id);

        if (stageOptional.isPresent()) {
            Stage stage = stageOptional.get();
            stage.setTitle(updatedStage.getTitle());
            stage.setDescription(updatedStage.getDescription());
            stageRepository.save(stage);
            return ResponseEntity.ok(stage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a stage by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        if (stageRepository.existsById(id)) {
            stageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    } */


