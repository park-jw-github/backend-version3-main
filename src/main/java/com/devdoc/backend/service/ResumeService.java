package com.devdoc.backend.service;

import com.devdoc.backend.dto.*;
import com.devdoc.backend.model.*;
import com.devdoc.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private CertificateRepository certificateRepository;


    // 이력서 저장
    @Transactional
    public void saveResume(int resumeId, ResumeDTO resumeDTO) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            resume.setTitle(resumeDTO.getTitle());

            List<Language> languages = resumeDTO.getLanguages().stream()
                    .map(languageDTO -> new Language(languageDTO.getId(), languageDTO.getLanguage(), languageDTO.getTestName(), languageDTO.getScore(), languageDTO.getDate(), resume))
                    .collect(Collectors.toList());
            resume.setLanguages(languages);

            List<Award> awards = resumeDTO.getAwards().stream()
                    .map(awardDTO -> new Award(awardDTO.getId(), awardDTO.getAwardName(), awardDTO.getAwardingInstitution(), awardDTO.getDate(), awardDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setAwards(awards);

            List<Certificate> certificates = resumeDTO.getCertificates().stream()
                    .map(certificateDTO -> new Certificate(certificateDTO.getId(), certificateDTO.getCertificateName(), certificateDTO.getIssuer(), certificateDTO.getIssueDate(), resume))
                    .collect(Collectors.toList());
            resume.setCertificates(certificates);

            List<Skill> skills = resumeDTO.getSkills().stream()
                    .map(skillDTO -> new Skill(skillDTO.getId(), skillDTO.getTechStack(), skillDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setSkills(skills);

            List<Career> careers = resumeDTO.getCareers().stream()
                    .map(careerDTO -> new Career(careerDTO.getId(), careerDTO.getCompany(), careerDTO.getDepartment(), careerDTO.getStartDate(), careerDTO.getEndDate(), careerDTO.getIsCurrent(), careerDTO.getTechStack(), careerDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setCareers(careers);

            List<Project> projects = resumeDTO.getProjects().stream()
                    .map(projectDTO -> new Project(projectDTO.getId(), projectDTO.getTitle(), projectDTO.getStartDate(), projectDTO.getEndDate(), projectDTO.getIsCurrent(), projectDTO.getIntro(), projectDTO.getTechStack(), projectDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setProjects(projects);

            resumeRepository.save(resume);
        }
    }

    // 특정 이력서 조회
    public ResumeDTO getResumeByResumeId(int resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();

            List<LanguageDTO> languageDTOs = resume.getLanguages().stream()
                    .map(language -> new LanguageDTO(language.getId(), language.getLanguage(), language.getTestName(), language.getScore(), language.getDate()))
                    .collect(Collectors.toList());

            List<AwardDTO> awardDTOs = resume.getAwards().stream()
                    .map(award -> new AwardDTO(award.getId(), award.getAwardName(), award.getAwardingInstitution(), award.getDate(), award.getDescription()))
                    .collect(Collectors.toList());

            List<CertificateDTO> certificateDTOs = resume.getCertificates().stream()
                    .map(certificate -> new CertificateDTO(certificate.getId(), certificate.getCertificateName(), certificate.getIssuer(), certificate.getIssueDate()))
                    .collect(Collectors.toList());

            List<SkillDTO> skillDTOs = resume.getSkills().stream()
                    .map(skill -> new SkillDTO(skill.getId(), skill.getTechStack(), skill.getDescription()))
                    .collect(Collectors.toList());

            List<CareerDTO> careerDTOs = resume.getCareers().stream()
                    .map(career -> new CareerDTO(career.getId(), career.getCompany(), career.getDepartment(), career.getStartDate(), career.getEndDate(), career.getIsCurrent(), career.getTechStack(), career.getDescription()))
                    .collect(Collectors.toList());

            List<ProjectDTO> projectDTOs = resume.getProjects().stream()
                    .map(project -> new ProjectDTO(project.getId(), project.getTitle(), project.getStartDate(), project.getEndDate(), project.getIsCurrent(), project.getIntro(), project.getTechStack(), project.getDescription()))
                    .collect(Collectors.toList());

            return new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt(), languageDTOs, awardDTOs, certificateDTOs, skillDTOs, careerDTOs, projectDTOs);
        }
        return null;
    }

    // 모든 이력서 조회
    public List<ResumeDTO> getAllResumes() {
        List<Resume> resumes = resumeRepository.findAll();
        return resumes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 이력서를 DTO로 변환
    private ResumeDTO convertToDTO(Resume resume) {
        List<LanguageDTO> languages = languageRepository.findByResumeId(resume.getId())
                .stream()
                .map(language -> new LanguageDTO(language.getId(), language.getLanguage(), language.getTestName(), language.getScore(), language.getDate()))
                .collect(Collectors.toList());

        List<AwardDTO> awards = awardRepository.findByResumeId(resume.getId())
                .stream()
                .map(award -> new AwardDTO(award.getId(), award.getAwardName(), award.getAwardingInstitution(), award.getDate(), award.getDescription()))
                .collect(Collectors.toList());

        List<CertificateDTO> certificates = certificateRepository.findByResumeId(resume.getId())
                .stream()
                .map(certificate -> new CertificateDTO(certificate.getId(), certificate.getCertificateName(), certificate.getIssuer(), certificate.getIssueDate()))
                .collect(Collectors.toList());

        List<SkillDTO> skills = skillRepository.findByResumeId(resume.getId())
                .stream()
                .map(skill -> new SkillDTO(skill.getId(), skill.getTechStack(), skill.getDescription()))
                .collect(Collectors.toList());

        List<CareerDTO> careers = careerRepository.findByResumeId(resume.getId())
                .stream()
                .map(career -> new CareerDTO(career.getId(), career.getCompany(), career.getDepartment(), career.getStartDate(), career.getEndDate(), career.getIsCurrent(), career.getTechStack(), career.getDescription()))
                .collect(Collectors.toList());

        List<ProjectDTO> projects = projectRepository.findByResumeId(resume.getId())
                .stream()
                .map(project -> new ProjectDTO(project.getId(), project.getTitle(), project.getStartDate(), project.getEndDate(), project.getIsCurrent(), project.getIntro(), project.getTechStack(), project.getDescription()))
                .collect(Collectors.toList());

        return new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt(), languages, awards, certificates, skills, careers, projects);
    }

    // 특정 사용자의 모든 이력서 조회
    public List<ResumeDTO> getAllResumesByUser(String userId) {
        List<Resume> resumes = resumeRepository.findByUserId(userId);
        return resumes.stream().map(resume -> new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt(), null, null, null, null, null, null)).collect(Collectors.toList());
    }

    // 새로운 이력서 생성
    @Transactional
    public Resume createResume(String title, String userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            Resume resume = new Resume();
            resume.setTitle(title);
            resume.setUser(user);
            resume = resumeRepository.save(resume);
            return resume;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // 이력서 삭제
    @Transactional
    public void deleteResumeByResumeId(int resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        optionalResume.ifPresent(resumeRepository::delete);
    }

    // 이력서 제목 저장
    @Transactional
    public ResumeDTO saveResumeTitleByResumeId(int resumeId, String newTitle) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            resume.setTitle(newTitle);
            resumeRepository.save(resume);
            return new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt(), null, null, null, null, null, null);
        }
        return null;
    }
}

