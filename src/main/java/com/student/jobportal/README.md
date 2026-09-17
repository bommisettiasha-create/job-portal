# 💼 Job Portal - Final Year Project (Placement Ready)

> A Complete Job Portal System built with Spring Boot, Thymeleaf & MySQL/H2 for Students and Recruiters. 
> Developed for Final Year B.Tech Project.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-blue)
![Status](https://img.shields.io/badge/Status-Completed-success)

🔗 **Live Demo:** `http://localhost:8080`
🔗 **GitHub:** `https://github.com/bommisettiasha-create/job-portal`

---

## ✨ All Features Implemented

### 1. Application Status Tracking
- Every application has status: `PENDING`, `SHORTLISTED`, `REJECTED`, `HIRED`
- Recruiter can update status with one click

### 2. Recruiter Applicants Page
- Route: `/applications`
- Shows all applicants with Name, Email, Phone, Resume, Status
- Action buttons: Shortlist / Reject

### 3. Student Dashboard Statistics
- Route: `/dashboard`
- Cards: Total Available Jobs, My Applications, Saved Jobs, Pending Applications
- Clean analytics for students

### 4. Save Jobs Feature ❤️
- Student can save any job with ❤️ Save button
- Saved jobs stored in database
- Route: `/saved` - View all saved jobs

### 5. Filters & Search
- **Search:** By Title, Company, Location (keyword search)
- **Filters:** Hyderabad, Bangalore, Remote, Pune - one click filter
- Route: `/filter?location=Hyderabad`

### 6. Validation
- `@NotBlank` validation for Job Title and Company
- `required` fields in HTML forms
- Email validation in Apply form
- Resume file type validation (PDF/DOC/DOCX only)

### 7. Admin Dashboard 🔐
- Route: `/admin`
- Stats Cards: Total Jobs, Total Applications, Total Saved, Pending, Shortlisted
- Admin can monitor full portal

### 8. Security
- Role-based access ready (Admin/Recruiter/Student)
- File upload security - resume stored with unique timestamp name
- Form validation to prevent empty data

### 9. Resume Upload
- Applicants upload resume while applying
- Stored in `/uploads/` folder with unique name
- Recruiter can see resume file name in applicants page

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17, Spring Boot 3, Spring MVC, Spring Data JPA |
| Frontend | Thymeleaf, HTML5, CSS3, Bootstrap 5.3 |
| Database | H2 Database (In-Memory) / MySQL ready |
| Build Tool | Maven |
| IDE | VS Code / IntelliJ / Eclipse |

---

## 🚀 How to Run

### Step 1: Clone
```bash
git clone https://github.com/bommisettiasha-create/job-portal.git
cd job-portal