package com.example.studentcrud.repository;

import com.example.studentcrud.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill,Long> {
    boolean existsByName(String name);

    Optional<Set<Skill>> findAllByIdIn(Set<Long> skillsIds);
}
