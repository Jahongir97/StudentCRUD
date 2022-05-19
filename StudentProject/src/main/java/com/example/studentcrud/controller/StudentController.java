package com.example.studentcrud.controller;

import com.example.studentcrud.errs.ResourceNotFoundException;
import com.example.studentcrud.payload.ApiResponse;
import com.example.studentcrud.payload.StudentDto;
import com.example.studentcrud.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(studentService.getByStudentId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewStudent(@Valid @RequestBody StudentDto studentDto) throws ResourceNotFoundException {
        ApiResponse apiResponse = studentService.addNewStudent(studentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentDto studentDto) throws ResourceNotFoundException {
        ApiResponse apiResponse = studentService.editStudent(id, studentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        ApiResponse apiResponse = studentService.deleteStudent(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
