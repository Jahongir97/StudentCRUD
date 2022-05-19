package com.example.studentcrud.controller;

import com.example.studentcrud.errs.ResourceNotFoundException;
import com.example.studentcrud.payload.ApiResponse;
import com.example.studentcrud.payload.SkillDto;
import com.example.studentcrud.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/skill")
public class SkillController {

    SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewSkill(@Valid @RequestBody SkillDto skillDto) throws ResourceNotFoundException {
        ApiResponse apiResponse = skillService.addNewSkill(skillDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editSkill(@PathVariable("id") Long id, @Valid @RequestBody SkillDto skillDto) throws ResourceNotFoundException {
        ApiResponse apiResponse = skillService.editSkill(id, skillDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSkillById(@PathVariable("id") Long id) {
        ApiResponse apiResponse = skillService.deleteSkillById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
