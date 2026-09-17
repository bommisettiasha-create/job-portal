package com.student.jobportal.controller;

import com.student.jobportal.model.Application;
import com.student.jobportal.model.Job;
import com.student.jobportal.service.ApplicationService;
import com.student.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class JobController {
    @Autowired private JobService jobService;
    @Autowired private ApplicationService applicationService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("jobs", jobService.searchJobs(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("jobs", jobService.getAllJobs());
        }
        model.addAttribute("job", new Job());
        return "index";
    }

    @PostMapping("/add")
    public String addJob(@ModelAttribute Job job) { jobService.saveJob(job); return "redirect:/"; }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) { jobService.deleteJob(id); return "redirect:/"; }

    @GetMapping("/edit/{id}")
    public String editJob(@PathVariable Long id, Model model) { model.addAttribute("job", jobService.getJobById(id)); return "edit"; }

    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute Job job) { job.setId(id); jobService.saveJob(job); return "redirect:/"; }

    @GetMapping("/view/{id}")
    public String viewJob(@PathVariable Long id, Model model) { model.addAttribute("job", jobService.getJobById(id)); return "details"; }

    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);
        model.addAttribute("application", new Application());
        return "apply";
    }

    @PostMapping("/apply/{id}")
    public String applyJob(@PathVariable Long id, @ModelAttribute Application application, @RequestParam("resumeFile") MultipartFile resumeFile) {
        try {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + resumeFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, resumeFile.getBytes());

            Job job = jobService.getJobById(id);
            application.setJobId(id);
            application.setJobTitle(job.getTitle());
            application.setResumeFileName(fileName);
            applicationService.saveApplication(application);
        } catch (Exception e) { e.printStackTrace(); }
        return "redirect:/applications";
    }

    @GetMapping("/applications")
    public String viewApplications(Model model) {
        model.addAttribute("applications", applicationService.getAllApplications());
        return "applications";
    }
}