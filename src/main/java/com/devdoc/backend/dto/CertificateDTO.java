package com.devdoc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {
    private Integer id;
    private String certificateName;
    private String issuer;
    private String issueDate;
}
