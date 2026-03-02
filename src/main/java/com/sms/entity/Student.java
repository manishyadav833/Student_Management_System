package com.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Name required")
    private String name;

    @Email(message="Invalid email")
    private String email;

    @NotBlank(message="Course required")
    private String course;

    private String photo;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}

    public String getCourse(){return course;}
    public void setCourse(String course){this.course=course;}

    public String getPhoto(){return photo;}
    public void setPhoto(String photo){this.photo=photo;}
}