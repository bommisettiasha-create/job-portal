package com.student.jobportal.controller;

import com.student.jobportal.model.Job;
import com.student.jobportal.repository.JobRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobController {

    @Autowired
    private JobRepository jobRepo;

    @GetMapping({"/", "/jobs"})
    public String listJobs(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("jobs", jobRepo.findAll());
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "index";
    }

    // ADD JOB PAGE OPEN
    @GetMapping("/jobs/add")
    public String addJobPage(Model model) {
        model.addAttribute("job", new Job());
        return "add-job";
    }

    // ADD JOB SAVE
    @PostMapping("/jobs/add")
    public String saveJob(@ModelAttribute Job job) {
        jobRepo.save(job);
        return "redirect:/jobs";
    }

    // APPLY NOW BUTTON
    @GetMapping("/jobs/apply/{id}")
    public String applyJob(@PathVariable Long id, Model model) {
        Job job = jobRepo.findById(id).orElse(null);
        model.addAttribute("job", job);
        return "apply";
    }
    
    @PostMapping("/jobs/apply/{id}")
    public String applySuccess(@PathVariable Long id, Model model) {
        Job job = jobRepo.findById(id).orElse(null);
        model.addAttribute("job", job);
        model.addAttribute("message", "Successfully Applied for " + job.getTitle() + " at " + job.getCompany() + "!");
        return "apply-success";
    }
}