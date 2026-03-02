package com.sms.controller;

import com.sms.entity.Student;
import com.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service){
        this.service=service;
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model){

        model.addAttribute("students",
                service.searchStudent(keyword));

        model.addAttribute("student", new Student());

        return "index";
    }
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("students",service.getAllStudents());
        model.addAttribute("student",new Student());
        return "index";
    }
    @PostMapping("/save")
    public String save(@Valid Student student,
                       BindingResult result,
                       @RequestParam(value="image", required=false) MultipartFile file,
                       Model model,
                       RedirectAttributes redirectAttributes) throws Exception {

        // validation error
        if(result.hasErrors()){
            model.addAttribute("students", service.getAllStudents());
            return "index";
        }

        // file upload
        if(file != null && !file.isEmpty()){

            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images/");

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            student.setPhoto(fileName);
        }

        service.saveStudent(student);

        // success message after redirect
        redirectAttributes.addFlashAttribute("success",
                "Student added successfully!");

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes){

        service.deleteStudent(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Student deleted successfully!");

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        model.addAttribute("student",service.getStudentById(id));
        return "edit";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        List<Student> students = service.getAllStudents();

        // total count
        model.addAttribute("totalStudents", students.size());

        // course wise grouping
        Map<String, Long> map =
                students.stream()
                        .collect(Collectors.groupingBy(
                                Student::getCourse,
                                Collectors.counting()));

        model.addAttribute("labels", new ArrayList<>(map.keySet()));
        model.addAttribute("data", new ArrayList<>(map.values()));

        return "dashboard";
    }

    @GetMapping("/login")
    public String login(){ return "login"; }
}