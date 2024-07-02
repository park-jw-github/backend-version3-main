package com.devdoc.backend.repository;

import com.devdoc.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByResumeId(Integer resumeId);

    Optional<Project> findByIdAndResumeId(Integer ProjectId, Integer resumeId);
}
