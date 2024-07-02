package com.devdoc.backend.service;

import com.devdoc.backend.dto.SkillDTO;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.model.Skill;
import com.devdoc.backend.repository.ResumeRepository;
import com.devdoc.backend.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private SkillRepository skillRepository;

    // Skill 항목 데이터 저장 또는 업데이트
    @Transactional
    public SkillDTO saveOrUpdateSkill(int resumeId, SkillDTO skillDTO) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            Skill skill = skillRepository.findByIdAndResumeId(skillDTO.getId(), resumeId)
                    .orElse(new Skill());

            boolean isNew = (skill.getId() == null); // 새로운 항목인지 확인

            skill.setTechStack(skillDTO.getTechStack());
            skill.setDescription(skillDTO.getDescription());
            skill.setResume(resume);

            Skill savedSkill = skillRepository.save(skill);

            return new SkillDTO(savedSkill.getId(), savedSkill.getTechStack(), savedSkill.getDescription());
        }
        throw new RuntimeException("Resume not found");
    }

    // Skill 항목 데이터 삭제
    @Transactional
    public void deleteSkill(int resumeId, int skillId) {
        Optional<Skill> skill = skillRepository.findByIdAndResumeId(skillId, resumeId);
        skill.ifPresent(skillRepository::delete);
    }
}
