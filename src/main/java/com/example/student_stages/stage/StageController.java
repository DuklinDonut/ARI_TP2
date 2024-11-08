package com.example.student_stages.stage;

import com.example.student_stages.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/stages")
public class StageController {

    @Autowired
    private StageRepository stageRepository;

    // Get all stages
    @GetMapping
    public ResponseEntity<List<Stage>> getAllStages() {
        System.out.println("Fetching all stages...");
        List<Stage> stages = (List<Stage>) stageRepository.findAll();
        if (stages.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no stages found
        }
        return ResponseEntity.ok(stages); // Return the list of stages
    }

    // Get a stage by ID
    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable Long id) {
        return stageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new stage (using POST)
    @PostMapping
    public ResponseEntity<Stage> createStage(@RequestParam String title, @RequestParam String description) {
        Stage stage = new Stage(title, description);
        Stage savedStage = stageRepository.save(stage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStage);
    }

    // Update a stage by ID
    @PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable Long id, @RequestBody Stage updatedStage) {
        if (!stageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedStage.setIdStage(id); // Ensure the ID is updated to avoid creating a new entry
        Stage savedStage = stageRepository.save(updatedStage);
        return ResponseEntity.ok(savedStage);
    }

    // Delete a stage by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        if (stageRepository.existsById(id)) {
            stageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
