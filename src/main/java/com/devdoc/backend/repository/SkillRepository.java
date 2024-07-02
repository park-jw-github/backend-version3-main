package com.devdoc.backend.repository;

import com.devdoc.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findByResumeId(Integer resumeId);

    Optional<Skill> findByIdAndResumeId(Integer SkillId, Integer resumeId);
}
