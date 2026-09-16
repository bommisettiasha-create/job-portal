package com.student.jobportal.controller;

import com.student.jobportal.model.Job;
import com.student.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;

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
    public String addJob(@ModelAttribute Job job) {
        jobService.saveJob(job);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/";
    }

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
}