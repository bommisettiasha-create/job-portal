package com.student.jobportal.controller;

import com.student.jobportal.model.Job;
import com.student.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
    @GetMapping("/") public String home(Model model) { model.addAttribute("jobs", jobService.getAllJobs()); return "index"; }
    @GetMapping("/add-job") public String showAddForm(Model model) { model.addAttribute("job", new Job()); return "add-job"; }
    @PostMapping("/save-job") public String saveJob(@Valid @ModelAttribute("job") Job job, BindingResult result) { if (result.hasErrors()) return "add-job"; jobService.saveJob(job); return "redirect:/"; }
    @GetMapping("/delete/{id}") public String deleteJob(@PathVariable Long id) { jobService.deleteJob(id); return "redirect:/"; }
}