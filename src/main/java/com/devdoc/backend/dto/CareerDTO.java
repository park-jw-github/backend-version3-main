package com.devdoc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerDTO {
    private Integer id;
    private String company;
    private String department;
    private String startDate;
    private String endDate;
    private Boolean isCurrent;
    private String techStack;
    private String description;
}
