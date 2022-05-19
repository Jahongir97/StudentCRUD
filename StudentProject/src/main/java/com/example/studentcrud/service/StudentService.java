package com.example.studentcrud.service;

import com.example.studentcrud.entity.Attachment;
import com.example.studentcrud.entity.Skill;
import com.example.studentcrud.entity.Student;
import com.example.studentcrud.errs.ResourceNotFoundException;
import com.example.studentcrud.payload.ApiResponse;
import com.example.studentcrud.payload.StudentDto;
import com.example.studentcrud.repository.AttachmentRepository;
import com.example.studentcrud.repository.SkillRepository;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AttachmentRepository attachmentRepository;
    private final SkillRepository skillRepository;


    public StudentService(StudentRepository studentRepository, AttachmentRepository attachmentRepository, SkillRepository skillRepository) {
        this.studentRepository = studentRepository;
        this.attachmentRepository = attachmentRepository;
        this.skillRepository = skillRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getByStudentId(Long id) throws ResourceNotFoundException {
        return getStudentById(id);
    }

    public ApiResponse addNewStudent(StudentDto studentDto) throws ResourceNotFoundException {
        boolean existsByEmail = studentRepository.existsByEmail(studentDto.getEmail());

        boolean existsByPhoneNumber = studentRepository.existsByPhoneNumber(studentDto.getPhoneNumber());

        if (existsByEmail)
            return new ApiResponse("Student with this email address already exists, please enter other email address", false);

        if (existsByPhoneNumber)
            return new ApiResponse("Student with this phone number already exists, please enter other email address", false);

        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setEmail(studentDto.getEmail());
        newStudent.setPhoneNumber(studentDto.getEmail());
        newStudent.setImage(studentDto.getAttachmentId() == null ? null :
                getAttachment(studentDto.getAttachmentId()));
        newStudent.setLongitude(studentDto.getLongitude());
        newStudent.setLatitude(studentDto.getLatitude());
        newStudent.setSkills(getSkillsSet(studentDto.getSkillIds()));
        studentRepository.save(newStudent);

        return new ApiResponse("Students saved", true);
    }

    public ApiResponse editStudent(Long studentId, StudentDto studentDto) throws ResourceNotFoundException {

        Student editingStudent = getStudentById(studentId);

        editingStudent.setEmail(studentDto.getEmail());
        editingStudent.setFirstName(studentDto.getFirstName());
        editingStudent.setLastName(studentDto.getLastName());
        editingStudent.setPhoneNumber(studentDto.getPhoneNumber());
        editingStudent.setImage(studentDto.getAttachmentId() == null ? editingStudent.getImage() :
                getAttachment(studentDto.getAttachmentId()));
        editingStudent.setLatitude(studentDto.getLatitude());
        editingStudent.setLongitude(studentDto.getLongitude());
        editingStudent.setSkills(getSkillsSet(studentDto.getSkillIds()));
        studentRepository.save(editingStudent);


        return new ApiResponse("Student successfully edited", true);
    }

    public ApiResponse deleteStudent(Long studentId) {
        try {
            studentRepository.deleteById(studentId);
            return new ApiResponse("Student successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error, Cannot delete a student", false);
        }
    }

    public Student getStudentById(Long studentId) throws ResourceNotFoundException {
        return studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with this id not found or does not exist"));
    }

    public Attachment getAttachment(Long attachmentId) throws ResourceNotFoundException {
        return attachmentRepository.findById(attachmentId).orElseThrow(() -> new ResourceNotFoundException("Image not found or does not exist"));
    }

    public Set<Skill> getSkillsSet(Set<Long> skillsId) throws ResourceNotFoundException {
        return skillRepository.findAllByIdIn(skillsId).orElseThrow(() -> new ResourceNotFoundException("Skills with this id not found or does not exist"));
    }
}
