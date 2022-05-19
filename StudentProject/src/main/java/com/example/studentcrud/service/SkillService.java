package com.example.studentcrud.service;

import com.example.studentcrud.entity.Skill;
import com.example.studentcrud.errs.ResourceNotFoundException;
import com.example.studentcrud.payload.ApiResponse;
import com.example.studentcrud.payload.SkillDto;
import com.example.studentcrud.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) throws ResourceNotFoundException {
        return getSkill(id);
    }

    public ApiResponse addNewSkill(SkillDto skillDto) {
        boolean exists = skillRepository.existsByName(skillDto.getName());
        if (exists) {
            return new ApiResponse("Such skill exists", false);
        }

        Skill skill = new Skill(
                skillDto.getName(),
                skillDto.getValue()==null?0:skillDto.getValue()
        );

        skillRepository.save(skill);
        return new ApiResponse("Skill saved", true);
    }

    public ApiResponse editSkill(Long skillId, SkillDto skillDto) throws ResourceNotFoundException {
        Skill editingSkill = getSkill(skillId);

        editingSkill.setName(skillDto.getName());
        editingSkill.setValue(skillDto.getValue());
        skillRepository.save(editingSkill);

        return new ApiResponse("Skill successfully edited", true);

    }

    public ApiResponse deleteSkillById(Long id) {
        try {
            skillRepository.deleteById(id);
            return new ApiResponse("Skill deleted successfully", true);
        } catch (Exception e) {
            return new ApiResponse("Cannot delete", false);
        }
    }


    public Skill getSkill(Long skillId) throws ResourceNotFoundException {
        return skillRepository.findById(skillId).orElseThrow(() -> new ResourceNotFoundException("Skill not found or does not exists"));

    }


}
