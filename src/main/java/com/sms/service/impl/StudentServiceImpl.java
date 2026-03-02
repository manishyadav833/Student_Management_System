package com.sms.service.impl;

import com.sms.entity.Student;
import com.sms.repository.StudentRepository;
import com.sms.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo){
        this.repo=repo;
    }

    public Student saveStudent(Student s){ return repo.save(s); }

    public List<Student> getAllStudents(){ return repo.findAll(); }

    public Student getStudentById(Long id){
        return repo.findById(id).orElseThrow();
    }

    public void deleteStudent(Long id){ repo.deleteById(id); }

    @Override
    public List<Student> searchStudent(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }
}