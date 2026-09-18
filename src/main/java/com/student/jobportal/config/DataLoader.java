package com.student.jobportal.config;

import com.student.jobportal.model.Job;
import com.student.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JobRepository jobRepo;

    @Override
    public void run(String... args) throws Exception {
        if (jobRepo.count() == 0) {
            jobRepo.save(new Job("Java Developer", "TCS", "Hyderabad", "Full Time", "Java, Spring Boot", 600000.0));
            jobRepo.save(new Job("Frontend React Developer", "Infosys", "Bangalore", "Remote", "React, HTML, CSS", 500000.0));
            jobRepo.save(new Job("Python Developer", "Wipro", "Pune", "Hybrid", "Python, Django", 700000.0));
            jobRepo.save(new Job("Data Analyst", "Amazon", "Hyderabad", "Full Time", "SQL, Python", 800000.0));
            jobRepo.save(new Job("Full Stack Developer", "Tech Mahindra", "Chennai", "Full Time", "Java + React", 900000.0));
            System.out.println("Sample Jobs Added Successfully!");
        }
    }
}