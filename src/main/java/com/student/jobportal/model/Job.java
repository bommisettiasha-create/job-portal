package com.student.jobportal.model;
import jakarta.persistence.*;

@Entity
@Table(name="job")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String company;
    private String location;
    private String jobType;
    @Column(length=2000)
    private String description;
    private Double salary;

    public Job() {}
    
    public Job(String title, String company, String location, String jobType, String description, Double salary) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.jobType = jobType;
        this.description = description;
        this.salary = salary;
    }

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getTitle(){return title;} public void setTitle(String title){this.title=title;}
    public String getCompany(){return company;} public void setCompany(String company){this.company=company;}
    public String getLocation(){return location;} public void setLocation(String location){this.location=location;}
    public String getJobType(){return jobType;} public void setJobType(String jobType){this.jobType=jobType;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public Double getSalary(){return salary;} public void setSalary(Double salary){this.salary=salary;}
}