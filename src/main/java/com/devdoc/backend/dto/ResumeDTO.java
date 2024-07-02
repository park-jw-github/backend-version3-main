package com.devdoc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private Integer id;
    private String title;
    private LocalDateTime createdAt;
    private List<LanguageDTO> languages;
    private List<AwardDTO> awards;
    private List<CertificateDTO> certificates;
    private List<SkillDTO> skills;
    private List<CareerDTO> careers;
    private List<ProjectDTO> projects;
}
