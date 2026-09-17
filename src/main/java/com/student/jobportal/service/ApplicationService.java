package com.student.jobportal.service;

import com.student.jobportal.model.Application;
import com.student.jobportal.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public void saveApplication(Application application) {
        applicationRepository.save(application);
    }
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}