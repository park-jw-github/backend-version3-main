package com.devdoc.backend.service;

import com.devdoc.backend.dto.CareerDTO;
import com.devdoc.backend.model.Career;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.repository.AwardRepository;
import com.devdoc.backend.repository.CareerRepository;
import com.devdoc.backend.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CareerService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private CareerRepository careerRepository;

    // Career 항목 데이터 저장 또는 업데이트
    @Transactional
    public CareerDTO saveOrUpdateCareer(int resumeId, CareerDTO careerDTO) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            Career career = careerRepository.findByIdAndResumeId(careerDTO.getId(), resumeId)
                    .orElse(new Career());

            boolean isNew = (career.getId() == null); // 새로운 항목인지 확인

            career.setCompany(careerDTO.getCompany());
            career.setDepartment(careerDTO.getDepartment());
            career.setStartDate(careerDTO.getStartDate());
            career.setEndDate(careerDTO.getEndDate());
            career.setIsCurrent(careerDTO.getIsCurrent());
            career.setTechStack(careerDTO.getTechStack());
            career.setDescription(careerDTO.getDescription());
            career.setResume(resume);

            Career savedCareer = careerRepository.save(career);

            return new CareerDTO(savedCareer.getId(), savedCareer.getCompany(), savedCareer.getDepartment(), savedCareer.getStartDate(), savedCareer.getEndDate(), savedCareer.getIsCurrent(), savedCareer.getTechStack(), savedCareer.getDescription());
        }
        throw new RuntimeException("Resume not found");
    }

    // Career 항목 데이터 삭제
    @Transactional
    public void deleteCareer(int resumeId, int careerId) {
        Optional<Career> career = careerRepository.findByIdAndResumeId(careerId, resumeId);
        career.ifPresent(careerRepository::delete);
    }
}
