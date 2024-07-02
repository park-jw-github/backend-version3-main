package com.devdoc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Integer id;
    private String title;
    private String startDate;
    private String endDate;
    private Boolean isCurrent;
    private String intro;
    private String techStack;
    private String description;
}
