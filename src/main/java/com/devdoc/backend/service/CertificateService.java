package com.devdoc.backend.service;

import com.devdoc.backend.dto.CertificateDTO;
import com.devdoc.backend.model.Certificate;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.repository.CertificateRepository;
import com.devdoc.backend.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CertificateService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    // Certificate 항목 데이터 저장 또는 업데이트
    @Transactional
    public CertificateDTO saveOrUpdateCertificate(int resumeId, CertificateDTO certificateDTO) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            // 자격증 목록 중 하나의 항목 불러오기 (없으면 새로 생성)
            Certificate certificate = certificateRepository.findByIdAndResumeId(certificateDTO.getId(), resumeId)
                    .orElse(new Certificate());

            boolean isNew = (certificate.getId() == null);

            // 항목에 대한 정보 입력
            certificate.setCertificateName(certificateDTO.getCertificateName());
            certificate.setIssuer(certificateDTO.getIssuer());
            certificate.setIssueDate(certificateDTO.getIssueDate());
            certificate.setResume(resume);

            // 입력한 내용을 저장
            Certificate savedCertificate = certificateRepository.save(certificate);

            return new CertificateDTO(savedCertificate.getId(), savedCertificate.getCertificateName(), savedCertificate.getIssuer(), savedCertificate.getIssueDate());
        }
        throw new RuntimeException("Resume not found");
    }

    // Certificate 항목 데이터 삭제
    @Transactional
    public void deleteCertificate(int resumeId, int certificateId) {
        Optional<Certificate> certificate = certificateRepository.findByIdAndResumeId(certificateId, resumeId);
        certificate.ifPresent(certificateRepository::delete);
    }
}
