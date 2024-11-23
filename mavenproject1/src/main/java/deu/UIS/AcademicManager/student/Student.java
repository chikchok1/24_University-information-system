/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager.student;

import deu.UIS.AcademicManager.*;

public class Student {
    private String name;
    private String studentNumber;
    private String department;
    private String grade;
    private String birthDate;
    private String phone;
    private String password; // 비밀번호 추가

    public Student(String name, String studentNumber, String department, String grade, String birthDate, String phone) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.department = department;
        this.grade = grade;
        this.birthDate = birthDate;
        this.phone = phone;
        this.password = ""; // 비밀번호 초기화
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}


