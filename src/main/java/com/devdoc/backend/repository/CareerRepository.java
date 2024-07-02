package com.devdoc.backend.repository;

import com.devdoc.backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Integer> {
    List<Career> findByResumeId(Integer resumeId);

    Optional<Career> findByIdAndResumeId(Integer CareerId, Integer resumeId);
}
