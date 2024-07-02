package com.devdoc.backend.service;

import com.devdoc.backend.dto.ProjectDTO;
import com.devdoc.backend.model.Project;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.repository.ProjectRepository;
import com.devdoc.backend.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Project 항목 데이터 저장 또는 업데이트
    @Transactional
    public ProjectDTO saveOrUpdateProject(int resumeId, ProjectDTO projectDTO) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            Project project = projectRepository.findByIdAndResumeId(projectDTO.getId(), resumeId)
                    .orElse(new Project());

            boolean isNew = (project.getId() == null); // 새로운 항목인지 확인

            project.setTitle(projectDTO.getTitle());
            project.setStartDate(projectDTO.getStartDate());
            project.setEndDate(projectDTO.getEndDate());
            project.setIsCurrent(projectDTO.getIsCurrent());
            project.setIntro(projectDTO.getIntro());
            project.setTechStack(projectDTO.getTechStack());
            project.setDescription(projectDTO.getDescription());
            project.setResume(resume);

            Project savedProject = projectRepository.save(project);

            return new ProjectDTO(savedProject.getId(), savedProject.getTitle(), savedProject.getStartDate(), savedProject.getEndDate(), savedProject.getIsCurrent(), savedProject.getIntro(), savedProject.getTechStack(), savedProject.getDescription());
        }
        throw new RuntimeException("Resume not found");
    }

    // Project 항목 데이터 삭제
    @Transactional
    public void deleteProject(int resumeId, int projectId) {
        Optional<Project> project = projectRepository.findByIdAndResumeId(projectId, resumeId);
        project.ifPresent(projectRepository::delete);
    }
}
