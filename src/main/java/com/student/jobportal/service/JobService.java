package com.student.jobportal.service;

import com.student.jobportal.model.Job;
import com.student.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() { 
        return jobRepository.findAll(); 
    }
    
    public void saveJob(Job job) { 
        jobRepository.save(job); 
    }
    
    public void deleteJob(Long id) { 
        jobRepository.deleteById(id); 
    }
    
    public Job getJobById(Long id) { 
        return jobRepository.findById(id).orElse(null); 
    }

    public List<Job> searchJobs(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return jobRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(keyword, keyword);
        }
        return jobRepository.findAll();
    }
}