package com.student.jobportal.controller;

import com.student.jobportal.model.Application;
import com.student.jobportal.model.Job;
import com.student.jobportal.model.SavedJob;
import com.student.jobportal.repository.ApplicationRepository;
import com.student.jobportal.repository.JobRepository;
import com.student.jobportal.repository.SavedJobRepository;
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
    @Autowired private JobRepository jobRepository;
    @Autowired private ApplicationRepository applicationRepository;
    @Autowired private SavedJobRepository savedJobRepository;

    // HOME + SEARCH
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

    // ADD JOB
    @PostMapping("/add")
    public String addJob(@ModelAttribute Job job) {
        jobService.saveJob(job);
        return "redirect:/";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editJob(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute Job job) {
        job.setId(id);
        jobService.saveJob(job);
        return "redirect:/";
    }

    // VIEW DETAILS
    @GetMapping("/view/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "details";
    }

    // APPLY FORM
    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);
        model.addAttribute("application", new Application());
        return "apply";
    }

    @PostMapping("/apply/{id}")
    public String applyJob(@PathVariable Long id, @ModelAttribute Application application,
                           @RequestParam("resumeFile") MultipartFile resumeFile) {
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
            application.setStatus("PENDING");
            applicationService.saveApplication(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/applications";
    }

    // RECRUITER APPLICANTS PAGE + STATUS UPDATE
    @GetMapping("/applications")
    public String viewApplications(Model model) {
        model.addAttribute("applications", applicationService.getAllApplications());
        return "applications";
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public String updateStatus(@PathVariable Long id, @PathVariable String status) {
        Application app = applicationRepository.findById(id).orElse(null);
        if (app != null) {
            app.setStatus(status);
            applicationRepository.save(app);
        }
        return "redirect:/applications";
    }

    // SAVE JOBS
    @GetMapping("/save/{id}")
    public String saveJob(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        SavedJob saved = new SavedJob();
        saved.setJobId(job.getId());
        saved.setJobTitle(job.getTitle());
        saved.setCompany(job.getCompany());
        savedJobRepository.save(saved);
        return "redirect:/saved";
    }

    @GetMapping("/saved")
    public String savedJobs(Model model) {
        model.addAttribute("savedJobs", savedJobRepository.findAll());
        return "saved";
    }

    // FILTERS
    @GetMapping("/filter")
    public String filterJobs(@RequestParam String location, Model model) {
        model.addAttribute("jobs", jobService.getAllJobs().stream()
                .filter(j -> j.getLocation().toLowerCase().contains(location.toLowerCase()))
                .toList());
        model.addAttribute("job", new Job());
        return "index";
    }

    // ADMIN DASHBOARD
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("totalJobs", jobRepository.count());
        model.addAttribute("totalApplications", applicationRepository.count());
        model.addAttribute("totalSaved", savedJobRepository.count());
        model.addAttribute("pendingCount", applicationRepository.findAll().stream().filter(a -> a.getStatus().equals("PENDING")).count());
        model.addAttribute("shortlistedCount", applicationRepository.findAll().stream().filter(a -> a.getStatus().equals("SHORTLISTED")).count());
        return "admin";
    }

    // STUDENT DASHBOARD STATISTICS
    @GetMapping("/dashboard")
    public String studentDashboard(Model model) {
        model.addAttribute("totalJobs", jobRepository.count());
        model.addAttribute("myApplications", applicationRepository.count());
        model.addAttribute("mySaved", savedJobRepository.count());
        model.addAttribute("pendingApps", applicationRepository.findAll().stream().filter(a -> a.getStatus().equals("PENDING")).count());
        return "dashboard";
    }
}