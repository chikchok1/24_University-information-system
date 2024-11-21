/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager;

/**
 *
 * @author YangJinWon
 */
public class Professor {
    private String name;
    private String professorNumber;
    private String department;
    private String birthDate;
    private String phone;
    private String password; // 비밀번호 추가

    public Professor(String name, String professorNumber, String department, String birthDate, String phone) {
        this.name = name;
        this.professorNumber = professorNumber;
        this.department = department;
        this.birthDate = birthDate;
        this.phone = phone;
        this.password = ""; // 비밀번호 초기화
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProfessorNumber() { return professorNumber; }
    public void setProfessorNumber(String professorNumber) { this.professorNumber = professorNumber; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

