package com.student.jobportal.repository;
import com.student.jobportal.model.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {}